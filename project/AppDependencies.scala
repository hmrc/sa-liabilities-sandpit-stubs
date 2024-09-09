import play.sbt.PlayImport.*
import sbt.*

object AppDependencies {

  private val bootstrapVersion = "9.1.0"
  private val PekkoVersion = "1.0.2"

  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-30"  % bootstrapVersion,
    "org.apache.pekko"        %% "pekko-stream"               % PekkoVersion,
    "com.github.javafaker"    % "javafaker"                   % "1.0.2"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-30"     % bootstrapVersion            % Test,
    "org.apache.pekko"        %% "pekko-stream-testkit"       % PekkoVersion                % Test
  )

  val it = Seq.empty
}
