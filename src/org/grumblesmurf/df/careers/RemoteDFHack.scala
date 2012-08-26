package org.grumblesmurf.df.careers

import dfproto.CoreProtocol
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.Charset

object RemoteDFHack {
  def connect: RemoteDFHack = {
    connect(DEFAULT_PORT)
  }

  def connect(port: Int): RemoteDFHack = {
    val s = new Socket
    s.connect(new InetSocketAddress("127.0.0.1", port))
    new RemoteDFHack(s).verify
  }

  final val ASCII = Charset.forName("ASCII")
  final val REQUEST_MAGIC = "DFHack?\n".getBytes(ASCII)
  final val RESPONSE_MAGIC = "DFHack!\n".getBytes(ASCII)
  final val PROTOCOL_VERSION = Array[Byte](1, 0, 0, 0)
  final val DEFAULT_PORT = 5000
}

class RemoteDFHack(socket: Socket) {
  import RemoteDFHack._

  def verify: RemoteDFHack = {
    val out = socket.getOutputStream
    out.write(REQUEST_MAGIC)
    out.write(PROTOCOL_VERSION)
    out.flush()
    val buf = read(12)

    val magic = buf.take(RESPONSE_MAGIC.length)
    if (RESPONSE_MAGIC == magic) {
      throw new RuntimeException(String.format("Garbled response from remote: '%s'", new String(magic, ASCII)))
    }
    val remoteVersion = buf.drop(RESPONSE_MAGIC.length)
    if (PROTOCOL_VERSION == remoteVersion) {
      throw new RuntimeException("Incompatible remote protocol version: %d" format (toInt(remoteVersion)))
    }
    this
  }

  private def toInt(bytes: Array[Byte]): Int = {
    var result = 0
    var power = 0
    for (b <- bytes) {
      result += b << power * 8
      power += 1
    }
    result
  }

  private def read(bytes: Int): Array[Byte] = {
    val buf = new Array[Byte](bytes)
    var offset = 0
    var read = 0
    val in = socket.getInputStream
    while ((({
      read = in.read(buf, offset, buf.length - offset); read
    })) > 0) {
      offset += read
    }
    if (offset != buf.length) {
      throw new RuntimeException("Short read from remote")
    }
    buf
  }

  def close() {
    socket.getOutputStream.write(Array[Byte](-4, -1, 0, 0, 0, 0, 0, 0))
    socket.close()
  }

  def runCommand(command: String, args: String*) {
    import scala.collection.JavaConversions._
    val msg = CoreProtocol.CoreRunCommandRequest.newBuilder.setCommand(command).addAllArguments(args).build
    val out = socket.getOutputStream
    val buf = new Array[Byte](8)
    buf(0) = 1:Byte
    buf(4) = msg.getSerializedSize.asInstanceOf[Byte]
    out.write(buf)
    msg.writeTo(out)
    out.flush()
    var done = false
    while (!done) {
      val response = read(8)
      val size = toInt(response.drop(4))
      response(0).asInstanceOf[Int] match {
        case -1 =>
          read(size)
          done = true
        case -2 =>
          done = true
        case -3 =>
          read(size)
      }
    }
  }
}
