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

import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.libs.json.*
import play.api.libs.json.Json.{toJson, writes}
import play.api.mvc.Codec
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*

case class BalanceDetail(
  payableAmount: PayableAmount,
  payableDueDate: PayableDueDate,
  pendingDueAmount: PendingDueAmount,
  pendingDueDate: PendingDueDate,
  overdueAmount: OverdueAmount,
  totalBalance: TotalBalance
)

object BalanceDetail:
  given Format[BalanceDetail]          = Json.format[BalanceDetail]
  given Writeable[List[BalanceDetail]] = Writeable(
    transform = data => Writeable.writeableOf_JsValue.transform(toJson(data)),
    contentType = Some(ContentTypes.JSON)
  )
