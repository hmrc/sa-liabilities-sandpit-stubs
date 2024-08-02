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

package uk.gov.hmrc.saliabilitiessandpitstubs.service

import uk.gov.hmrc.saliabilitiessandpitstubs.generator.BalanceDetailGenerator
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.BalanceDetailGenerator.generate
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService.details

trait BalanceDetailService(using BalanceDetailGenerator):

  val balanceDetailsByNino: String => Option[BalanceDetail | Seq[BalanceDetail]] = details.get

object BalanceDetailService extends BalanceDetailService(using BalanceDetailGenerator):

  private val details: Map[String, BalanceDetail | Seq[BalanceDetail]] = Map(
    "AA000000A" -> BalanceDetail(
      payableAmount = PayableAmount(100.00),
      payableDueDate = PayableDueDate("2024-07-20"),
      pendingDueAmount = PendingDueAmount(100.02),
      pendingDueDate = PendingDueDate("2024-08-20"),
      overdueAmount = OverdueAmount(100.03),
      totalBalance = TotalBalance(300.5)
    ),
    "AA000000B" -> BalanceDetail(
      payableAmount = PayableAmount(200.00),
      payableDueDate = PayableDueDate("2024-07-20"),
      pendingDueAmount = PendingDueAmount(200.02),
      pendingDueDate = PendingDueDate("2024-08-20"),
      overdueAmount = OverdueAmount(200.03),
      totalBalance = TotalBalance(600.5)
    ),
    "AA000000C" -> generate,
    "AA000000D" -> Seq.fill(2)(generate),
    "AA000000E" -> Seq.fill(3)(generate)
  )
