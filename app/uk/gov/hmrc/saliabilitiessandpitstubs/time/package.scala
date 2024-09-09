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

package uk.gov.hmrc.saliabilitiessandpitstubs

import java.time.LocalDate
import javax.inject.Inject
import scala.util.Random

package object time:
  class DefaultFutureDateGenerator @Inject() ()(using SystemLocalDate, Random) extends FutureDateGenerator

  object LiveSystemLocalDate extends SystemLocalDate:
    override def now: LocalDate = LocalDate.now()

  class StubbedSystemLocalDate(initialDate: String) extends SystemLocalDate:
    private val datePattern = raw"(\d{4})-(\d{2})-(\d{2})".r

    override def now: LocalDate = initialDate match
      case datePattern(year, month, day) => LocalDate.of(year.toInt, month.toInt, day.toInt)
      case _                             => throw new IllegalArgumentException(s"Invalid date format: $initialDate")
