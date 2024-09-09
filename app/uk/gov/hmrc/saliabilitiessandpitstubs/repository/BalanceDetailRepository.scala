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

package uk.gov.hmrc.saliabilitiessandpitstubs.repository

import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.repository.source.Persistence

trait BalanceDetailRepository:
  self: Persistence =>

  val findByNino: String => Option[BalanceDetail | Seq[BalanceDetail]] = store get (_: String)

  val addOrUpdate: (String, BalanceDetail) => Unit = (nino: String, balanceDetail: BalanceDetail) =>
    store = store get nino match
      case Some(existingDetail: BalanceDetail)       => store + (nino -> Seq(existingDetail, balanceDetail))
      case Some(existingDetails: Seq[BalanceDetail]) => store + (nino -> (existingDetails :+ balanceDetail))
      case None                                      => store + (nino -> balanceDetail)

  val deleteByNino: (String) => Option[Unit] = nino => (store get nino).map(_ => store = store - nino)

  val replace: (String, BalanceDetail) => Option[Unit] = (nino: String, balanceDetail: BalanceDetail) =>
    (store get nino).map(existing => store = store + (nino -> balanceDetail))

  def getAll: Map[String, BalanceDetail | Seq[BalanceDetail]] = store
