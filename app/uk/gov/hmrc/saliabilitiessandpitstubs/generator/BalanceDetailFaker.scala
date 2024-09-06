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

import com.github.javafaker.Faker
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*
import uk.gov.hmrc.saliabilitiessandpitstubs.time.FakerExtensions

import java.time.{Clock, LocalDate, ZoneId}
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.DAYS

trait BalanceDetailFaker(using faker: Faker) extends BalanceDetailGenerator:
  self: FakerExtensions =>

  def generate: BalanceDetail =
    val payableDueDate   = PayableDueDate(faker dateInFuture (30, DAYS))
    val pendingDueDate   = PendingDueDate(faker dateInFuture (60, DAYS))
    val overdueAmount    = OverdueAmount(faker.number() numberBetween (0, 10000))
    val payableAmount    = PayableAmount(faker.number() numberBetween (1000, 10000))
    val pendingDueAmount = PendingDueAmount(faker.number() numberBetween (100, 5000))
    val totalBalance     = TotalBalance(payableAmount ++ pendingDueAmount ++ overdueAmount)

    BalanceDetail(payableAmount, payableDueDate, pendingDueAmount, pendingDueDate, overdueAmount, totalBalance)
