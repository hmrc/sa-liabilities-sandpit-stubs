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
import uk.gov.hmrc.saliabilitiessandpitstubs.models.{BalanceDetail, *}

trait BalanceDetailService(using generator: BalanceDetailGenerator):
  val balanceDetailsByNino: String => Option[BalanceDetail | Seq[BalanceDetail]] = details.get

  private val details: Map[String, BalanceDetail | Seq[BalanceDetail]] = Map(
    "AA000000A" -> generator.generate,
    "AA000000B" -> generator.generate,
    "AA000000C" -> Seq.fill(2)(generator.generate),
    "AA000000D" -> Seq.fill(4)(generator.generate)
  )
