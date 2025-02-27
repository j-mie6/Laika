
E-Books (EPUB & PDF)
====================

Laika puts equal emphasis on the generation of e-books as on website generation.

Like all functionality, e-book generation does not require installation of external tools.
The EPUB format is supported by Laika's own Scala implementation, 
whereas PDF support is partially based on [Apache FOP].

The latter is not based on LaTeX like many other PDF renderers, but uses XSL-FO as an interim format.
The generation of this interim format happens in Laika's own renderers, 
only the final transformation step to the binary PDF format is delegated to Apache FOP.

[Apache FOP]: https://xmlgraphics.apache.org/fop/


Generating E-Books
------------------

If you are using the sbt plugin you can use several of its task for generating e-books:

* `laikaEPUB` or `laikaPDF` for transforming a directory of input files to a single output format
* `laikaGenerate epub pdf <other formats>` for transforming a directory of input files to EPUB and PDF
  and other output formats with a single parse operation
* `laikaSite` for generating a site that also contains PDF and/or EPUB files for download
  when setting the `laikaIncludeEPUB` and/or `laikaIncludePDF` settings to true.

See [sbt Plugin] for more details.

When using the library API, the `EPUB` and `PDF` renderers can be passed to the `Transformer` or `Renderer` APIs:

```scala mdoc:silent
import cats.effect.IO
import laika.api._
import laika.format._
import laika.io.implicits._
import laika.markdown.github.GitHubFlavor

val transformer = Transformer
  .from(Markdown)
  .to(EPUB)
  .using(GitHubFlavor)
  .parallel[IO]
  .build

transformer.use {
  _.fromDirectory("src")
   .toFile("hello.epub")
   .transform
}
```

See [Library API] for more details on these APIs and ensure you have the necessary dependencies in place -
the `laika-pdf` module for PDF or the `laika-io` module for EPUB - see [Dependencies].


Directory Structure
-------------------

An e-book generated by Laika is always a single, binary file, even when the input is an entire directory of markup files.
But the directory structure will be reflected in the navigation elements inside the e-book.

Markup files from the input directory will form the content of the e-book 
while static files like images and fonts will be embedded into the output 
when they are referenced from within one or more markup files. 

For EPUB specifically it is important to consider what file size to choose for your content.
The directory structure inside the generated EPUB container (which is essentially a glorified ZIP) 
will mirror exactly the structure of the input directory,
apart from the additional metadata files it needs to generate.
Therefore it is best to avoid very large markup files as they might slow down the experience in the e-book reader,
and instead distribute content over a higher number of smaller input files.

See [Document Types] for general info about the various types of input files Laika supports,
and [Supported Document Types] in this chapter for additional info specific to EPUB and PDF.


Configuration
-------------

The most convenient way to configure aspects like metadata, look & feel and navigation for your e-books
is to use the built-in default theme called Helium and its settings.

There is a dedicated [Theme Settings] chapter, so we'll just link the most relevant aspects that can be configured
for e-books from here.

* The [Fonts] to embed in EPUB and PDF files in case you do not want to use Helium's default fonts.

* The [Colors] for the main theme and for syntax highlighting.

* Several aspects of the theme's [Layout], like line height, block spacing or PDF page sizes.

* [Metadata] (title, authors, description, language, etc.) to include in the generated e-books in a way that reader
  software can expose.

* Configure [Cover Images for E-books] or [Auto-Linking CSS & JS Files].


### Book Navigation

Laika supports a directory structure with sub-directories of any depth. 
Markup files from the input directory will form the content of the e-book, 
linearized in depth-first traversal and using your configured [Navigation Order][Configuration Files].

Laika will generate navigation elements compatible with e-book readers, as shown in the images below:

PDF Navigation in Preview for Mac:

@:image(../img/nav-pdf.png) {
  alt = PDF Navigation
  style = small-image
  intrinsicWidth = 311
  intrinsicHeight = 311
}

EPUB Navigation in iBooks:

@:image(../img/nav-epub.png) {
  alt = EPUB Navigation
  style = small-image
  intrinsicWidth = 473
  intrinsicHeight = 470
}

The navigation depth can be configured with the Helium API:

```scala mdoc:silent
import laika.helium.Helium

Helium.defaults
  .epub.navigationDepth(4)
  .pdf.navigationDepth(4)
```

@:callout(info) 

The default for EPUB is just 2 levels as some readers like iBooks mess with the hierarchy of navigation items
when using more than 2 levels.
If you increase this setting make sure you verify it's looking good in the targeted readers.

@:@

Note that this affects the navigation structure that will be generated for the navigation tools of the respective
EPUB or PDF readers.

You might additionally want to insert a table of content into the page flow, right after the cover image,
as this would be the only navigation structure that would be available when printing the document.
See [Table of Contents] how to configure such a structure which can also have a different navigation depth
than the tree generated for the readers.


Supported Document Types
------------------------

You can also place images, fonts and other supported file types into the input directory.
Laika will embed these files in the generated EPUB container and/or PDF file.

The supported file types / suffixes are:

**For EPUB**

* Images: `jpg`, `jpeg`, `gif`, `png`, `svg`
* Audio: `mp3`, `mp4`
* HTML: `html`, `xhtml`
* JavaScript: `js`
* CSS: `css`   
* Fonts: `woff2`, `woff`, `ttf`, `otf` 

**For PDF**

* Images: `jpg`, `jpeg`, `gif`, `png`, `svg`, `bmp`, `tiff`
* Fonts: `pfb` (Type 1), `ttf`, `otf` 


CSS for EPUB
------------

Since content files for EPUB are standard XHTML files (apart from optional EPUB-specific attributes), 
you can style your e-books with standard CSS. 

It is sufficient to simply place all CSS into the input directory, alongside the text markup and other file types. 
References to these CSS files will be automatically added to the header section of all generated HTML files. 

To enable a distinction between EPUB and website generation in case you want to produce both with the same inputs,
Laika expects the following suffixes for CSS files:

* Files ending with `.epub.css` will only be linked in HTML files for EPUB, not for the site
* File ending with `.shared.css` will be linked for both
* All other files ending with `.css` will only be used for website content

When referencing images or fonts from your CSS files, you can use relative paths, 
as the directory layout will be retained inside the EPUB container.


JavaScript for EPUB
-------------------

The scope of support for JavaScript may depend on the target reader, so early testing is recommended when
scripting EPUB documents.

It is sufficient to simply place all JavaScript into the input directory, alongside the text markup and other file types. 
References to these JavaScript files will be automatically added to the header section of all generated HTML files. 

To enable a distinction between EPUB and website generation in case you want to produce both with the same inputs,
Laika expects the following suffixes for JavaScript files:

* Files ending with `.epub.js` will only be linked in HTML files for EPUB, not for the site
* File ending with `.shared.js` will be linked for both
* All other files ending with `.js` will only be used for website content

In case you want to create a custom EPUB template you need to provide some indicator in the config header whether
the template needs scripting support, as each scripted document needs a flag in the OPF metadata for the EPUB container.
The key for the attribute is `laika.epub.scripted` and valid values are `always`, `never`, `auto`.
The `auto` value which is also used in Helium's default template sets the flag whenever there are any
documents in the input tree with the suffix `.epub.js` or `.shared.js`.


CSS for PDF
-----------

Laika offers the unusual, but convenient feature of CSS styling for PDF.
It allows for customization in a syntax familiar to most users. 

The CSS files need to be placed into the root directory of your input tree with a name
in the format `<name>.fo.css`.

However, as PDF is a page-based format and in Laika's case expects `XSL-FO` as an interim format,
there are a few subtle differences:

* All CSS attributes must also be valid XSL-FO attributes. 
  There is quite a big overlap, so you can use the familiar `font-family`, `font-weight`, `font-size`
  attributes as well as most of the border, padding and margin attributes.
  For an overview over the available attributes you can refer to the [Formatting Properties][fo-props] chapter
  in the XSL-FO specification.
  
* While id selectors and class selectors function like in web CSS, the type selectors are quite different.
  Since there is no interim HTML result, the types you can refer to are not HTML tag names like `div`,
  but the class names of the Laika AST nodes, e.g. `Header` or `Paragraph`.
  See [The Document AST] for details.
  
* Some selector types of the CSS specification are not supported as most of them do not add much value
  in the context of a Laika document tree:
  
    * Pseudo-classes like `:hover`
    * Attribute selectors like `[attribute~=value]` (since Laika nodes do not have many properties)
    * `Element1+Element2` or `Element1~Element2` for selecting based on sibling elements

[fo-props]: http://www.w3.org/TR/xsl11/#pr-section

An example for styling a level-2 header:

```css
Header.level2 {
  font-family: sans-serif;
  font-weight: bold;
  font-size: 14pt;
}
```

The `Header` type selector refers to the name of the AST node Laika produces for headers.
The `level2` class is a style that gets rendered for each header node to be able to style levels differently

Only a few of Laika's nodes get rendered with class attributes. 
You can examine HTML output to get an overview over rendered attributes as they are largely identical to those
rendered for EPUB and PDF. 

Likewise, out of the box only a subset of nodes get rendered with an auto-generated id: 
headers, footnotes or citations.


Customized Output
-----------------

In cases where styling with CSS alone is not sufficient, there are additional,
lower-level hooks to customize the rendered output.

### Templates

You can place custom default templates into the root directory of the input tree,
named `default.template.epub.xhtml` for EPUB and `default.template.fo` for PDF.

For EPUB the template will be applied to each rendered file individually,
for PDF the generated XSL-FO will first be concatenated and then the template will be applied 
to the single final FO document.

Customizing the PDF template would require knowledge of `XSL-FO`, but is hopefully rarely ever
necessary as PDFs can be styled by Laika's [CSS for PDF] feature.

See [Creating Templates] for general info about Laika's template engine.


### Overriding Renderers

Apart from adjusting the surrounding template the AST nodes will be rendered into, 
you can also customize how each individual AST node itself is rendered. 
 
This is a general customization hook in the library and not different from overriding renderers for site output.
See [Overriding Renderers] for more details.
