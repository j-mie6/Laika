/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package laika.helium.generate

import laika.ast.RelativePath
import laika.ast.Path.Root
import laika.helium.config.{ ColorSet, DarkModeSupport, EPUBSettings, LandingPage, SiteSettings }
import laika.theme.config.FontDefinition

private[helium] object CSSVarGenerator {

  private val darkModeMediaQuery = "@media (prefers-color-scheme: dark) {"

  private def generateFontFace(fontDef: FontDefinition, path: RelativePath): String =
    s"""@font-face {
       |  font-family: "${fontDef.family}";
       |  font-weight: ${fontDef.weight.value.toLowerCase};
       |  font-style: ${fontDef.style.value.toLowerCase};
       |  src: url("$path");
       |}""".stripMargin

  def generate(settings: SiteSettings): String = {
    import settings.layout._
    val layoutStyles = Seq(
      "content-width"  -> contentWidth.displayValue,
      "nav-width"      -> navigationWidth.displayValue,
      "top-bar-height" -> topBarHeight.displayValue
    )
    generate(
      settings,
      layoutStyles,
      includeInverted = true,
      settings.content.landingPage
    )
  }

  def generate(settings: EPUBSettings): String = {
    val embeddedFonts = settings.bookConfig.fonts.flatMap { font =>
      font.resource.embedResource.map { res =>
        generateFontFace(font, res.path.relativeTo(Root / "helium" / "laika-helium.epub.css"))
      }
    }.mkString("", "\n\n", "\n\n")
    embeddedFonts + generate(
      settings,
      Nil,
      includeInverted = false,
      landingPage = None
    )
  }

  private def toVars(pairs: Seq[(String, String)]): Seq[(String, String)] = pairs.map {
    case (name, value) => (s"--$name", value)
  }

  private val invertedColorSet: Seq[(String, String)] =
    toVars(
      Seq(
        "component-color"   -> ref("primary-medium"),
        "component-area-bg" -> ref("primary-color"),
        "component-hover"   -> ref("bg-color"),
        "component-border"  -> ref("primary-light")
      )
    )

  private val darkHighlight  = "rgba(0, 0, 0, 0.05)"
  private val whiteHighlight = "rgba(255, 255, 255, 0.15)"

  private def renderStyles(
      styles: Seq[(String, String)],
      includeInverted: Boolean,
      darkMode: Boolean
  ): String = {
    val renderedStyles = styles.map { case (name, value) =>
      s"$name: $value;"
    }

    def renderInverted(start: String, sep: String, end: String): String = if (includeInverted)
      (invertedColorSet :+ ("--subtle-highlight" -> (if (darkMode) darkHighlight
                                                     else whiteHighlight)))
        .map { case (name, value) =>
          s"$name: $value;"
        }
        .mkString(start, sep, end)
    else if (darkMode) "}\n\n"
    else ""

    if (darkMode)
      renderedStyles.mkString(s"$darkModeMediaQuery\n  :root {\n    ", "\n    ", "\n  }\n") +
        renderInverted(s"\n  .dark-inverted {\n    ", "\n    ", "\n  }\n}\n\n")
    else
      renderedStyles.mkString(":root {\n  ", "\n  ", "\n}\n\n") +
        renderInverted(".light-inverted {\n  ", "\n  ", "\n}\n\n")
  }

  private def ref(name: String): String = s"var(--$name)"

  def colorSet(colors: ColorSet, darkMode: Boolean): Seq[(String, String)] = {
    import colors._
    Seq(
      "primary-color"          -> theme.primary.displayValue,
      "primary-light"          -> theme.primaryLight.displayValue,
      "primary-medium"         -> theme.primaryMedium.displayValue,
      "secondary-color"        -> theme.secondary.displayValue,
      "text-color"             -> theme.text.displayValue,
      "bg-color"               -> theme.background.displayValue,
      "gradient-top"           -> theme.bgGradient._1.displayValue,
      "gradient-bottom"        -> theme.bgGradient._2.displayValue,
      "component-color"        -> ref("primary-color"),
      "component-area-bg"      -> ref("primary-light"),
      "component-hover"        -> ref("secondary-color"),
      "component-border"       -> ref("primary-medium"),
      "subtle-highlight"       -> (if (darkMode) whiteHighlight else darkHighlight),
      "messages-info"          -> messages.info.displayValue,
      "messages-info-light"    -> messages.infoLight.displayValue,
      "messages-warning"       -> messages.warning.displayValue,
      "messages-warning-light" -> messages.warningLight.displayValue,
      "messages-error"         -> messages.error.displayValue,
      "messages-error-light"   -> messages.errorLight.displayValue,
      "syntax-base1"           -> syntaxHighlighting.base.c1.displayValue,
      "syntax-base2"           -> syntaxHighlighting.base.c2.displayValue,
      "syntax-base3"           -> syntaxHighlighting.base.c3.displayValue,
      "syntax-base4"           -> syntaxHighlighting.base.c4.displayValue,
      "syntax-base5"           -> syntaxHighlighting.base.c5.displayValue,
      "syntax-wheel1"          -> syntaxHighlighting.wheel.c1.displayValue,
      "syntax-wheel2"          -> syntaxHighlighting.wheel.c2.displayValue,
      "syntax-wheel3"          -> syntaxHighlighting.wheel.c3.displayValue,
      "syntax-wheel4"          -> syntaxHighlighting.wheel.c4.displayValue,
      "syntax-wheel5"          -> syntaxHighlighting.wheel.c5.displayValue
    )
  }

  def landingPageLayout(landingPage: LandingPage): Seq[(String, String)] = Seq(
    "landing-subtitle-font-size" -> landingPage.subtitleFontSize.displayValue,
    "teaser-title-font-size"     -> landingPage.teaserTitleFontSize.displayValue,
    "teaser-body-font-size"      -> landingPage.teaserBodyFontSize.displayValue
  )

  def generate(
      common: DarkModeSupport,
      additionalVars: Seq[(String, String)],
      includeInverted: Boolean,
      landingPage: Option[LandingPage]
  ): String = {
    import common._
    val vars =
      colorSet(common.colors, darkMode = false) ++
        Seq(
          "body-font"         -> ("\"" + themeFonts.body + "\", sans-serif"),
          "header-font"       -> ("\"" + themeFonts.headlines + "\", sans-serif"),
          "code-font"         -> ("\"" + themeFonts.code + "\", monospace"),
          "body-font-size"    -> fontSizes.body.displayValue,
          "code-font-size"    -> fontSizes.code.displayValue,
          "small-font-size"   -> fontSizes.small.displayValue,
          "title-font-size"   -> fontSizes.title.displayValue,
          "header2-font-size" -> fontSizes.header2.displayValue,
          "header3-font-size" -> fontSizes.header3.displayValue,
          "header4-font-size" -> fontSizes.header4.displayValue,
          "block-spacing"     -> common.layout.defaultBlockSpacing.displayValue,
          "line-height"       -> common.layout.defaultLineHeight.toString
        ) ++
        additionalVars ++
        landingPage.fold(Seq[(String, String)]())(landingPageLayout)

    val (colorScheme, darkModeStyles) = common.darkMode match {
      case Some(darkModeColors) =>
        (
          Seq(("color-scheme", "light dark")),
          renderStyles(
            toVars(colorSet(darkModeColors, darkMode = true)),
            includeInverted,
            darkMode = true
          )
        )
      case None                 => (Nil, "")
    }

    renderStyles(toVars(vars) ++ colorScheme, includeInverted, darkMode = false) + darkModeStyles
  }

}
