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

package uk.gov.hmrc.saliabilitiessandpitstubs.http

import org.apache.pekko.stream.scaladsl.FileIO.fromPath
import org.apache.pekko.stream.scaladsl.Source
import play.api.libs.json.Json.parse
import play.api.libs.json.{Json, Reads}

import java.nio.file.Path

trait FileStreamliner[T](implicit fjs: Reads[T]) extends Framing:

  def extractBalanceDetailsSource(path: Path): Source[T, _] = fromPath(path)
    .via(networkFramingDelimiter)
    .map(_.utf8String)
    .map(parse)
    .map(_.as[T])
