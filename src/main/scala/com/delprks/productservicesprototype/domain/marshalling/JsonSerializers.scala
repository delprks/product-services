package com.delprks.productservicesprototype.domain.marshalling

import java.text.SimpleDateFormat

import com.delprks.productservicesprototype.domain.Status
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.Serialization
import org.json4s.prefs.EmptyValueStrategy
import org.json4s.{DefaultFormats, Formats, jackson}

trait JsonSerializers extends Json4sSupport {
  private val defaultJsonFormat = new DefaultFormats {
    override val emptyValueStrategy = EmptyValueStrategy.preserve

    private val ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    override def dateFormatter = new SimpleDateFormat(ISO8601)
  }

  private val enumerationSerializers = Seq(
    new EnumNameSerializer(Status)
  )

  implicit val serialization: Serialization.type = jackson.Serialization
  implicit val formats: Formats = defaultJsonFormat ++ enumerationSerializers ++ org.json4s.ext.JodaTimeSerializers.all
}
