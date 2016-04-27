package org.bassethound.app.output.location

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

import org.bassethound.app.output.AggregateType
import org.bassethound.util.Files

object HtmlReport {
  private val baseFilename = "report"
  private final val Aggregate = "\\{aggregate\\}".r
  private final val PayloadFilename = "\\{payloadFilename\\}".r
  private final val DateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")
  def report(m: String , aggregateType: AggregateType) = {

    val resource = getClass.getResourceAsStream(s"/$baseFilename.html")
    val template = scala.io.Source.fromInputStream(resource) mkString ""
    val date = DateFormat.format(Calendar.getInstance().getTime)
    val filename = s"${baseFilename}_${aggregateType}_$date"
    val payloadFilename = s"$filename.json"

    val withAggregateType = Aggregate.replaceAllIn(template,aggregateType.toString)
    val withPayloadFilename= PayloadFilename.replaceAllIn(withAggregateType,payloadFilename)

    Files.write(m,new File(payloadFilename))
    Files.write(withPayloadFilename,new File(s"$filename.html"))
  }

}
