package poi

import org.apache.poi.hssf.usermodel._
import java.io._

object PoiTest {
  def main(args: Array[String]) {
    val wb = new HSSFWorkbook
    val sheet = wb.createSheet("sheet name hoge2")

    for (i <- 0 to 9) {
      val row = sheet.createRow(i)
      for(j <- 0 to 9 ) row.createCell(j).setCellValue(10*i+j)
    }

    val filename = "workbook.xlsx"
    val out = new FileOutputStream(filename)
    wb.write(out)
    out.close()
  }
}
