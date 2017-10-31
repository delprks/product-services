package com.delprks.productservicesprototype.domain

import java.sql.Timestamp

import org.joda.time.DateTime

object Status extends Enumeration {
  type Type = Value

  val Available = Value("available")
  val Pending = Value("pending")
  val Expired = Value("expired")
  val Cancelled = Value("cancelled")

  def apply(from: Timestamp, to: Timestamp, status: Option[String] = None): Status.Value = {
    val timestampToDate = (timestamp: Timestamp) => new DateTime(timestamp)
    val now = DateTime.now()

    if (status.getOrElse("") == Cancelled.toString) {
      Cancelled
    } else if (timestampToDate(from).isBefore(now) && timestampToDate(to).isAfter(now)) {
      Available
    } else if (timestampToDate(from).isAfter(now)) {
      Pending
    } else {
      Expired
    }
  }
}
