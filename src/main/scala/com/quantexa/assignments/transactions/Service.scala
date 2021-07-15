package com.quantexa.assignments.transactions

import com.quantexa.assignments.transactions.OutputFormat.{Question1Result, Question2Result, Question3Result}
import com.quantexa.assignments.transactions.TransactionsAssignment.Transaction

object Service {
  //Question1
  def Question1(inputData:List[Transaction])={
    inputData.groupBy(_.transactionDay)
      .mapValues(_.map(_.transactionAmount).sum).toList
      .map(x=>Question1Result(x._1,x._2)).sortBy(_.transactionDay)
  }

  //Question2
  def Question2(inputData:List[Transaction])={
    inputData.groupBy(_.accountId)
      .map(x=>Question2Result(x._1,x._2.groupBy(_.category).map(x=>(x._1,getAverage(x._2)))))
      .toList.sortBy(_.accountId)
  }

  //Question3
  def Question3(inputData: List[Transaction]) = {
    (for {
      x <- 1 to 29
      dayStats <- calculatePreviousFiveDaysStats(inputData, x)
    } yield dayStats).toList
  }

  def calculatePreviousFiveDaysStats(inputData: List[Transaction], day: Int) = {
    val daysRange = createDateRange(day)
    daysRange match {
      case None => List()
      case _ =>
        val inputForDays = inputData.filter(t => daysRange.get.toList.contains(t.transactionDay))
        val inputByAccount = inputForDays.groupBy(_.accountId)

        lazy val maxTransactions = inputByAccount
          .map(tsByAccount => (tsByAccount._1, tsByAccount._2.map(_.transactionAmount).max))

        lazy val average = inputByAccount
          .map(tsByAccount => (tsByAccount._1, getAverage(tsByAccount._2)))

        def totalFor(accountId: String, cat: String) = inputByAccount
          .map(tsByAccount => (
            tsByAccount._1,
            tsByAccount._2.filter(_.category == cat).map(_.transactionAmount).sum))
          .filter(_._1 == accountId).head._2

        inputByAccount
          .map(tsByAccount =>Question3Result(
            day,
            tsByAccount._1,
            maxTransactions(tsByAccount._1),
            average(tsByAccount._1),
            totalFor(tsByAccount._1, "AA"),
            totalFor(tsByAccount._1, "CC"),
            totalFor(tsByAccount._1, "FF")
          )).toList.sortBy(_.accountId)
    }
  }

  def createDateRange(inputDay: Int): Option[Range] = {
    inputDay match {
      case x if x <= 1 => None
      case y if y <= 5 => Some(Range(1, inputDay))
      case _ => Some(Range(inputDay - 5, inputDay))
    }
  }
  //Average
  private def getAverage(transactions: List[Transaction]): Double = {
    transactions.map(_.transactionAmount).sum / transactions.length
  }
}
