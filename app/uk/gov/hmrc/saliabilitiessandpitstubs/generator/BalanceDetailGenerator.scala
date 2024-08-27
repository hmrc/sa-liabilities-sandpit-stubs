/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.saliabilitiessandpitstubs.generator

import uk.gov.hmrc.saliabilitiessandpitstubs.models.*
import uk.gov.hmrc.saliabilitiessandpitstubs.time.LocalDateExtensions
import uk.gov.hmrc.saliabilitiessandpitstubs.time.LocalDateExtensions.nextDayInFuture

import scala.util.Random
import scala.util.Random.nextInt

trait BalanceDetailGenerator(using LocalDateExtensions)(random: Random):

  private def randomInRange(range: Range): Int =
    val start = Math.min(range.start, range.end)
    val end   = Math.max(range.start, range.end)

    start + random.nextInt((end - start) + 1)

  def generate: BalanceDetail =
    val pendingDueDate   = PendingDueDate(nextDayInFuture(monthsToAdd = 3))
    val payableDueDate   = PayableDueDate(nextDayInFuture(monthsToAdd = 6))
    val overdueAmount    = OverdueAmount(randomInRange(0 to 9999))
    val payableAmount    = PayableAmount(randomInRange(0 to 9999))
    val pendingDueAmount = PendingDueAmount(randomInRange(0 to 9999))
    val totalBalance     = TotalBalance(payableAmount ++ pendingDueAmount ++ overdueAmount)

    BalanceDetail(payableAmount, payableDueDate, pendingDueAmount, pendingDueDate, overdueAmount, totalBalance)
