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

package laika.helium.builder

/** The SVG icons used by Helium (the other ones are currently based on icon fonts).
  *
  * @author Jens Halm
  */
private[helium] object SVGIcons {

  val apiIcon =
    """<svg class="svg-icon" width="100%" height="100%" viewBox="0 0 100 100" version="1.1" xmlns="http://www.w3.org/2000/svg" xml:space="preserve" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linejoin:round;stroke-miterlimit:2;">
      |  <g class="svg-shape">
      |    <path d="M75,47.5c13.246,0 24,10.754 24,24c0,13.246 -10.754,24 -24,24c-13.246,0 -24,-10.754 -24,-24c0,-13.246 10.754,-24 24,-24Zm-50,-0c13.246,-0 24,10.754 24,24c0,13.246 -10.754,24 -24,24c-13.246,-0 -24,-10.754 -24,-24c0,-13.246 10.754,-24 24,-24Zm2.705,16.735l7.239,0l0.622,-4.904l-21.833,0l-0,4.904l7.589,0l0,22.067l6.383,0l-0,-22.067Zm58.076,7.265c-0,-8.757 -3.698,-14.166 -10.781,-14.166c-7.083,-0 -10.781,5.604 -10.781,14.166c0,8.757 3.698,14.166 10.781,14.166c7.083,0 10.781,-5.604 10.781,-14.166Zm-6.539,0c0,6.538 -1.128,9.496 -4.242,9.496c-2.997,0 -4.242,-2.88 -4.242,-9.496c-0,-6.616 1.206,-9.496 4.242,-9.496c3.036,-0 4.242,2.88 4.242,9.496Zm-29.242,-67c13.246,0 24,10.754 24,24c0,13.246 -10.754,24 -24,24c-13.246,0 -24,-10.754 -24,-24c0,-13.246 10.754,-24 24,-24Zm0.512,9.834c-7.122,-0 -12.609,5.098 -12.609,14.127c-0,9.263 5.215,14.205 12.532,14.205c4.164,0 7.083,-1.634 9.068,-3.658l-2.88,-3.697c-1.518,1.206 -3.153,2.413 -5.838,2.413c-3.697,-0 -6.266,-2.763 -6.266,-9.263c-0,-6.616 2.724,-9.379 6.149,-9.379c2.102,-0 3.892,0.778 5.371,1.984l3.113,-3.775c-2.257,-1.868 -4.748,-2.957 -8.64,-2.957Z"/>
      |  </g>
      |</svg>""".stripMargin

  val githubIcon =
    """<svg class="svg-icon" width="100%" height="100%" viewBox="0 0 100 100" version="1.1" xmlns="http://www.w3.org/2000/svg" xml:space="preserve" style="fill-rule:evenodd;clip-rule:evenodd;stroke-linejoin:round;stroke-miterlimit:2;">
      |  <g class="svg-shape">
      |    <path d="M49.995,1c-27.609,-0 -49.995,22.386 -49.995,50.002c-0,22.09 14.325,40.83 34.194,47.444c2.501,0.458 3.413,-1.086 3.413,-2.412c0,-1.185 -0.043,-4.331 -0.067,-8.503c-13.908,3.021 -16.843,-6.704 -16.843,-6.704c-2.274,-5.773 -5.552,-7.311 -5.552,-7.311c-4.54,-3.103 0.344,-3.042 0.344,-3.042c5.018,0.356 7.658,5.154 7.658,5.154c4.46,7.64 11.704,5.433 14.552,4.156c0.454,-3.232 1.744,-5.436 3.174,-6.685c-11.102,-1.262 -22.775,-5.553 -22.775,-24.713c-0,-5.457 1.949,-9.92 5.147,-13.416c-0.516,-1.265 -2.231,-6.348 0.488,-13.233c0,0 4.199,-1.344 13.751,5.126c3.988,-1.108 8.266,-1.663 12.518,-1.682c4.245,0.019 8.523,0.574 12.517,1.682c9.546,-6.47 13.736,-5.126 13.736,-5.126c2.728,6.885 1.013,11.968 0.497,13.233c3.204,3.496 5.141,7.959 5.141,13.416c0,19.209 -11.691,23.436 -22.83,24.673c1.795,1.544 3.394,4.595 3.394,9.26c0,6.682 -0.061,12.076 -0.061,13.715c0,1.338 0.899,2.894 3.438,2.406c19.853,-6.627 34.166,-25.354 34.166,-47.438c-0,-27.616 -22.389,-50.002 -50.005,-50.002"/>
      |  </g>
      |</svg>""".stripMargin

  val mastodonIcon =
    """<svg class="svg-icon" width="100%" height="100%" viewBox="0 0 16 16" version="1.1" xmlns="http://www.w3.org/2000/svg">
      |  <g class="svg-shape">
      |    <path d="M11.19 12.195c2.016-.24 3.77-1.475 3.99-2.603.348-1.778.32-4.339.32-4.339 0-3.47-2.286-4.488-2.286-4.488C12.062.238 10.083.017 8.027 0h-.05C5.92.017 3.942.238 2.79.765c0 0-2.285 1.017-2.285 4.488l-.002.662c-.004.64-.007 1.35.011 2.091.083 3.394.626 6.74 3.78 7.57 1.454.383 2.703.463 3.709.408 1.823-.1 2.847-.647 2.847-.647l-.06-1.317s-1.303.41-2.767.36c-1.45-.05-2.98-.156-3.215-1.928a3.614 3.614 0 0 1-.033-.496s1.424.346 3.228.428c1.103.05 2.137-.064 3.188-.189zm1.613-2.47H11.13v-4.08c0-.859-.364-1.295-1.091-1.295-.804 0-1.207.517-1.207 1.541v2.233H7.168V5.89c0-1.024-.403-1.541-1.207-1.541-.727 0-1.091.436-1.091 1.296v4.079H3.197V5.522c0-.859.22-1.541.66-2.046.456-.505 1.052-.764 1.793-.764.856 0 1.504.328 1.933.983L8 4.39l.417-.695c.429-.655 1.077-.983 1.934-.983.74 0 1.336.259 1.791.764.442.505.661 1.187.661 2.046v4.203z"/>
      |  </g>
      |</svg>""".stripMargin

}
