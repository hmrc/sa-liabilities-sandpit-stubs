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

package uk.gov.hmrc.saliabilitiessandpitstubs.data

import uk.gov.hmrc.saliabilitiessandpitstubs.models.*

object BalanceDetailTestDataFactory {

  val aValidBalanceDetail: BalanceDetail = BalanceDetail(
    payableAmount = PayableAmount(BigDecimal(100.00)),
    payableDueDate = PayableDueDate("2024-07-20"),
    pendingDueAmount = PendingDueAmount(BigDecimal(100.02)),
    pendingDueDate = PendingDueDate("2024-08-20"),
    overdueAmount = OverdueAmount(BigDecimal(100.03)),
    totalBalance = TotalBalance(BigDecimal(300.5))
  )

  val aValidBalanceDetailGeneratedUsingSeed: BalanceDetail = BalanceDetail(
    payableAmount = PayableAmount(BigDecimal(8884)),
    payableDueDate = PayableDueDate("2024-10-16"),
    pendingDueAmount = PendingDueAmount(BigDecimal(9970)),
    pendingDueDate = PendingDueDate("2024-09-26"),
    overdueAmount = OverdueAmount(BigDecimal(1248)),
    totalBalance = TotalBalance(BigDecimal(20102))
  )

}
