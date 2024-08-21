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

package uk.gov.hmrc.saliabilitiessandpitstubs.config

import com.google.inject.AbstractModule
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.BalanceController
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.AuthorizationActionFilter
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.BalanceDetailGenerator
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.DefaultBalanceDetailGenerator
import uk.gov.hmrc.saliabilitiessandpitstubs.service.BalanceDetailService

import scala.util.Random

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[AppConfig]).asEagerSingleton()
    bind(classOf[BalanceController]).asEagerSingleton()
    bind(classOf[BalanceDetailGenerator]).to(classOf[DefaultBalanceDetailGenerator]).asEagerSingleton()
    bind(classOf[AuthorizationActionFilter]).toProvider(classOf[AuthActionProvider]).asEagerSingleton()
    bind(classOf[Random]).toProvider(classOf[RandomProvider]).asEagerSingleton()
  }
}
