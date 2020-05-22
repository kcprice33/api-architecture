/*
 * Copyright 2020 HM Revenue & Customs
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

package config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig


trait AppConfig {
  def desBaseUrl: String

  def mtdIdBaseUrl: String

  def desEnv: String

  def desToken: String

  def apiGatewayContext: String

  def apiStatus(version: String): String

  def featureSwitch: Option[Configuration]

  def endpointsEnabled: Boolean
}

@Singleton
class AppConfigImpl @Inject()(config: ServicesConfig, configuration: Configuration) extends AppConfig {

  val mtdIdBaseUrl: String = config.baseUrl("mtd-id-lookup")
  val desBaseUrl: String = config.baseUrl("des")
  val desEnv: String = config.getString("microservice.services.des.env")
  val desToken: String = config.getString("microservice.services.des.token")

  val apiGatewayContext: String = config.getString("api.gateway.context")

  def apiStatus(version: String): String = config.getString(s"api.$version.status")

  def featureSwitch: Option[Configuration] = configuration.getOptional[Configuration](s"feature-switch")

  val endpointsEnabled: Boolean = config.getBoolean("api-definitions.endpoints.enabled")
}

trait FixedConfig {
  // Minimum tax year for MTD
  val minimumTaxYearBFLoss = 2019
  val minimumTaxYearLossClaim = 2020
}
