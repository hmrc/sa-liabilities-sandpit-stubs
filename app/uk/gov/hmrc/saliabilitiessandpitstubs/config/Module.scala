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

import com.github.javafaker.Faker
import com.google.inject.{AbstractModule, TypeLiteral}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.{BalanceCommandController, BalanceQueryController}
import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.AuthorizationActionFilter
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.*
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.repository.BalanceDetailRepository
import uk.gov.hmrc.saliabilitiessandpitstubs.repository.InMemoryBalanceDetailRepository
import uk.gov.hmrc.saliabilitiessandpitstubs.service.{BalanceDetailService, DefaultBalanceDetailService}
import uk.gov.hmrc.saliabilitiessandpitstubs.time.{DefaultFutureDateGenerator, FutureDateGenerator, SystemLocalDate}
import uk.gov.hmrc.saliabilitiessandpitstubs.utils.*

import scala.util.Random

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[Faker]).asEagerSingleton()
    bind(classOf[DatabaseInitializer]).asEagerSingleton()
    bind(classOf[AppConfig]).asEagerSingleton()
    bind(classOf[BalanceQueryController]).asEagerSingleton()
    bind(classOf[BalanceCommandController]).asEagerSingleton()
    bind(classOf[Random]).toProvider(classOf[RandomProvider]).asEagerSingleton()
    bind(classOf[BalanceDetailFaker]).to(classOf[DefaultBalanceDetailFaker]).asEagerSingleton()
    bind(classOf[BalanceDetailService]).to(classOf[DefaultBalanceDetailService]).asEagerSingleton()
    bind(classOf[FutureDateGenerator]).to(classOf[DefaultFutureDateGenerator]).asEagerSingleton()
    bind(classOf[DiscreteDelayDistributionStrategy])
      .to(classOf[DefaultDiscreteDelayDistributionStrategy])
      .asEagerSingleton()
    bind(classOf[LogDelayDistributionStrategy]).to(classOf[DefaultLogDelayDistributionStrategy]).asEagerSingleton()
    bind(classOf[NormalDelayDistributionStrategy])
      .to(classOf[DefaultNormalDelayDistributionStrategy])
      .asEagerSingleton()
    bind(classOf[AlwaysSuccessfulSimulatorStrategy])
      .toInstance(AlwaysSuccessfulSimulatorStrategy)
    bind(classOf[BalanceDetailRepository]).toInstance(InMemoryBalanceDetailRepository)
    bind(classOf[AuthorizationActionFilter]).toProvider(classOf[AuthActionProvider]).asEagerSingleton()
    bind(classOf[DelaySimulator]).toProvider(classOf[DelaySimulatorProvider]).asEagerSingleton()
    bind(classOf[SystemLocalDate]).toProvider(classOf[LocalDateProvider]).asEagerSingleton()
    bind(classOf[BalanceDetailRandomize]).to(classOf[DefaultBalanceDetailGenerator]).asEagerSingleton()
    bind(classOf[BalanceDetailInitialGeneratorResolver])
      .to(classOf[DefaultBalanceDetailInitialGeneratorResolver])
      .asEagerSingleton()
    bind(classOf[BalanceDetailGeneratorResolver]).to(classOf[DefaultBalanceDetailGeneratorResolver]).asEagerSingleton()
    bind(new TypeLiteral[JsValidator[BalanceDetail]]() {})
      .toProvider(classOf[BalanceDetailValidatorRequestProvider])
      .asEagerSingleton()
  }
}
