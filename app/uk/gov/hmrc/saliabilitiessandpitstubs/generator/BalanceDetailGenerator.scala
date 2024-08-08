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

trait BalanceDetailGenerator(using LocalDateExtensions):

  def generate(nino: String): BalanceDetail =
    val random           = new Random(nino.hashCode())
    val poundsToPence    = BigDecimal(100)
    val pendingDueDate   = PendingDueDate(nextDayInFuture(random.between(0, 90)))
    val payableDueDate   = PayableDueDate(nextDayInFuture(random.between(0, 180)))
    val overdueAmount    = OverdueAmount(BigDecimal(random.between(-999, 999)) / poundsToPence)
    val payableAmount    = PayableAmount(BigDecimal(random.between(-9999, 9999)) / poundsToPence)
    val pendingDueAmount = PendingDueAmount(BigDecimal(random.between(-99999, 99999)) / poundsToPence)
    val totalBalance     = TotalBalance(payableAmount ++ pendingDueAmount ++ overdueAmount)

    BalanceDetail(payableAmount, payableDueDate, pendingDueAmount, pendingDueDate, overdueAmount, totalBalance)

object BalanceDetailGenerator extends BalanceDetailGenerator(using LocalDateExtensions)
