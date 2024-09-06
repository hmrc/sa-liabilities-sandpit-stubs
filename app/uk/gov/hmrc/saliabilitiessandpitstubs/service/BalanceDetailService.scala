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

import play.api.mvc.{AnyContent, Request}
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.{BalanceDetailGeneratorResolver, BalanceDetailRandomize}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.*

trait BalanceDetailService(using generator: BalanceDetailRandomize, res: BalanceDetailGeneratorResolver):

  private var details: Map[String, BalanceDetail | Seq[BalanceDetail]] = Map(
    "AA000000A" -> generator.generate,
    "AA000000B" -> generator.generate,
    "AA000000C" -> Seq.fill(2)(generator.generate),
    "AA000000D" -> Seq.fill(4)(generator.generate)
  )

  val balanceDetailsByNino: String => Option[BalanceDetail | Seq[BalanceDetail]] = details.get(_: String)

  val addOrUpdateBalanceDetail: (String, BalanceDetail) => Unit = (nino: String, balanceDetail: BalanceDetail) =>
    details = details get nino match
      case Some(existingDetail: BalanceDetail)       => details + (nino -> Seq(existingDetail, balanceDetail))
      case Some(existingDetails: Seq[BalanceDetail]) => details + (nino -> (existingDetails :+ balanceDetail))
      case None                                      => details + (nino -> balanceDetail)

  def addOrUpdateGeneratedBalanceDetail(nino: String)(implicit request: Request[AnyContent]): Unit =
    addOrUpdateBalanceDetail(nino, res.generate(request))
