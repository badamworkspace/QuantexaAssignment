/*
  Quantexa Copyright Statement
 */

package com.quantexa.assignments.transactions

import scala.io.Source
import com.quantexa.assignments.transactions.Service._

/***
  * This Object holds the functions required for the Quantexa coding exercise and an entry point to execute them.
  * Once executed each question is executed in turn printing results to console
  */
  
object TransactionsAssignment extends App {

  /***
    * A case class to represent a transaction
    * @param transactionId The transaction Identification String
    * @param accountId The account Identification String
    * @param transactionDay The day of the transaction
    * @param category The category of the transaction
    * @param transactionAmount The transaction value
    */
  case class Transaction(
                          transactionId: String,
                          accountId: String,
                          transactionDay: Int,
                          category: String,
                          transactionAmount: Double)

  //The full path to the file to import
  val fileName = getClass.getResource("/transactions.csv").getPath

  //The lines of the CSV file (dropping the first to remove the header)
  Source.fromInputStream(getClass.getResourceAsStream("/transactions.csv")).getLines().drop(1)
  val transactionLines: Iterator[String] = Source.fromFile(fileName).getLines().drop(1)

  //Here we split each line up by commas and construct Transactions
    val transactions: List[Transaction] = transactionLines.map { line =>
    val split = line.split(',')
    Transaction(split(0), split(1), split(2).toInt, split(3), split(4).toDouble)
    }.toList
  println("Question1 solution :- ")
  Question1(transactions).map(println)
  println("\n\n\nQuestion2 solution :- ")
  Question2(transactions).map(println)
  println("\n\n\nQuestion3 Solution :- ")
  Question3(transactions).map(println)
  /*
   * 
   * END PROVIDED CODE
   * 
   */

 

}