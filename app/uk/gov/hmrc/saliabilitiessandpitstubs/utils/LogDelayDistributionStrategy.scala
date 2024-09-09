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

package uk.gov.hmrc.saliabilitiessandpitstubs.utils

import uk.gov.hmrc.saliabilitiessandpitstubs.config.NetworkConditionConfig

import scala.concurrent.Future
import scala.concurrent.Future.successful
import scala.math.{exp, max}
import scala.util.Random

trait LogDelayDistributionStrategy(using random: Random, config: NetworkConditionConfig) extends DelaySimulator:
  self: DelayStrategy =>

  override def simulateNetworkConditions[T](result: T): Future[T] =
    val logNormalValue = random.nextGaussian
    val probability    = exp(logNormalValue * config.standardDeviation + config.mean)

    math min (max(probability, 0.0), 1.0) match
      case p if p < config.failureProbability                                                                => simulateFailureWithDelay(result)
      case p if p < config.failureProbability + config.randomDelayProbability                                => simulateRandomDelay(result)
      case p if p < config.failureProbability + config.randomDelayProbability + config.throttlingProbability =>
        simulateThrottling(result)
      case p
          if p < config.failureProbability + config.randomDelayProbability + config.throttlingProbability + config.timeoutProbability =>
        simulateTimeout()
      case _                                                                                                 => successful(result)
