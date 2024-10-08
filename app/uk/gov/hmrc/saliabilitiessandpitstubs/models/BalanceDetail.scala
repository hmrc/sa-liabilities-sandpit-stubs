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

package uk.gov.hmrc.saliabilitiessandpitstubs.models

import play.api.http.{ContentTypes, Writeable}
import play.api.libs.json.*
import play.api.libs.json.Json.toJson
import uk.gov.hmrc.saliabilitiessandpitstubs.json.{StringBasedJsonOps, bigDecimalBasedReads, bigDecimalBasedWrites}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*

import scala.annotation.targetName
import scala.collection.immutable.Iterable

case class BalanceDetail(
  payableAmount: Option[PayableAmount] = None,
  payableDueDate: Option[PayableDueDate] = None,
  pendingDueAmount: Option[PendingDueAmount] = None,
  pendingDueDate: Option[PendingDueDate] = None,
  overdueAmount: Option[OverdueAmount] = None,
  totalBalance: Option[TotalBalance] = None
)

object BalanceDetail:
  def create(
    payableAmount: String,
    payableDueDate: String,
    pendingDueAmount: String,
    pendingDueDate: String,
    overdueAmount: String,
    totalBalance: String
  ): BalanceDetail =
    new BalanceDetail(
      Some(PayableAmount(BigDecimal.apply(payableAmount))),
      Some(PayableDueDate(payableDueDate)),
      Some(PendingDueAmount(BigDecimal.apply(pendingDueAmount))),
      Some(PendingDueDate(pendingDueDate)),
      Some(OverdueAmount(BigDecimal.apply(overdueAmount))),
      Some(TotalBalance(BigDecimal.apply(totalBalance)))
    )

  def apply(
    payableAmount: PayableAmount,
    payableDueDate: PayableDueDate,
    pendingDueAmount: PendingDueAmount,
    pendingDueDate: PendingDueDate,
    overdueAmount: OverdueAmount,
    totalBalance: TotalBalance
  ): BalanceDetail =
    new BalanceDetail(
      Some(payableAmount),
      Some(payableDueDate),
      Some(pendingDueAmount),
      Some(pendingDueDate),
      Some(overdueAmount),
      Some(totalBalance)
    )

  given Format[BalanceDetail] = Json.format[BalanceDetail]

  @targetName("UnionOfWriteableIterableBalanceDetail")
  given Writeable[Iterable[BalanceDetail] | BalanceDetail] =
    val toJson: (Iterable[BalanceDetail] | BalanceDetail) => JsValue =
      case single: BalanceDetail             => Json.toJson(single)
      case iterable: Iterable[BalanceDetail] => Json.toJson(iterable)

    Writeable(
      transform = data => Writeable.writeableOf_JsValue.transform(toJson(data)),
      contentType = Some(ContentTypes.JSON)
    )
