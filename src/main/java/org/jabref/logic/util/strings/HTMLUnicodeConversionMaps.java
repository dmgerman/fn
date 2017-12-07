begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.strings
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_class
DECL|class|HTMLUnicodeConversionMaps
specifier|public
class|class
name|HTMLUnicodeConversionMaps
block|{
comment|// most of the LaTeX commands can be read at http://en.wikibooks.org/wiki/LaTeX/Accents
comment|// The symbols can be seen at http://www.fileformat.info/info/unicode/char/a4/index.htm. Replace "a4" with the U+ number
comment|// http://detexify.kirelabs.org/classify.html and http://www.ctan.org/tex-archive/info/symbols/comprehensive/ might help to find the right LaTeX command
comment|// http://llg.cubic.org/docs/ent2latex.html and http://www.w3.org/TR/xml-entity-names/byalpha.html are also useful
DECL|field|HTML_LATEX_CONVERSION_MAP
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|HTML_LATEX_CONVERSION_MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|ESCAPED_ACCENTS
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|ESCAPED_ACCENTS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|UNICODE_ESCAPED_ACCENTS
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|UNICODE_ESCAPED_ACCENTS
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|NUMERICAL_LATEX_CONVERSION_MAP
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|NUMERICAL_LATEX_CONVERSION_MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|UNICODE_LATEX_CONVERSION_MAP
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|UNICODE_LATEX_CONVERSION_MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|LATEX_HTML_CONVERSION_MAP
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|LATEX_HTML_CONVERSION_MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|LATEX_UNICODE_CONVERSION_MAP
specifier|public
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|LATEX_UNICODE_CONVERSION_MAP
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/*   Portions Â© International Organization for Standardization 1986:      Permission to copy in any form is granted for use with      conforming SGML systems and applications as defined in      ISO 8879, provided this notice is included in all copies.      */
comment|// as well as http://www.w3.org/Math/characters/unicode.xml
comment|// An array of arrays of strings in the format:
comment|// {"decimal number of HTML entity", "text HTML entity", "corresponding LaTeX command"}
comment|// Leaving a field empty is OK as it then will not be included
DECL|field|CONVERSION_LIST
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|CONVERSION_LIST
init|=
operator|new
name|String
index|[]
index|[]
block|{
block|{
literal|"160"
block|,
literal|"nbsp"
block|,
literal|"{~}"
block|}
block|,
comment|// no-break space = non-breaking space,
comment|//                                 U+00A0 ISOnum
block|{
literal|"161"
block|,
literal|"iexcl"
block|,
literal|"{\\textexclamdown}"
block|}
block|,
comment|// inverted exclamation mark, U+00A1 ISOnum
block|{
literal|"162"
block|,
literal|"cent"
block|,
literal|"{\\textcent}"
block|}
block|,
comment|// cent sign, U+00A2 ISOnum
block|{
literal|"163"
block|,
literal|"pound"
block|,
literal|"{\\pounds}"
block|}
block|,
comment|// pound sign, U+00A3 ISOnum
block|{
literal|"164"
block|,
literal|"curren"
block|,
literal|"{\\textcurrency}"
block|}
block|,
comment|// currency sign, U+00A4 ISOnum
block|{
literal|"165"
block|,
literal|"yen"
block|,
literal|"{\\textyen}"
block|}
block|,
comment|// yen sign = yuan sign, U+00A5 ISOnum
block|{
literal|"166"
block|,
literal|"brvbar"
block|,
literal|"{\\textbrokenbar}"
block|}
block|,
comment|// broken bar = broken vertical bar,
comment|//                                 U+00A6 ISOnum
block|{
literal|"167"
block|,
literal|"sect"
block|,
literal|"{{\\S}}"
block|}
block|,
comment|// section sign, U+00A7 ISOnum
block|{
literal|"168"
block|,
literal|"uml"
block|,
literal|"{\\\"{}}"
block|}
block|,
comment|// diaeresis = spacing diaeresis,
comment|//                                 U+00A8 ISOdia
block|{
literal|"169"
block|,
literal|"copy"
block|,
literal|"{\\copyright}"
block|}
block|,
comment|// copyright sign, U+00A9 ISOnum
block|{
literal|"170"
block|,
literal|"ordf"
block|,
literal|"{\\textordfeminine}"
block|}
block|,
comment|// feminine ordinal indicator, U+00AA ISOnum
block|{
literal|"171"
block|,
literal|"laquo"
block|,
literal|"{\\guillemotleft}"
block|}
block|,
comment|// left-pointing double angle quotation mark
comment|//                                 = left pointing guillemet, U+00AB ISOnum
block|{
literal|"172"
block|,
literal|"not"
block|,
literal|"$\\neg$"
block|}
block|,
comment|// not sign, U+00AC ISOnum
block|{
literal|"173"
block|,
literal|"shy"
block|,
literal|"\\-"
block|}
block|,
comment|// soft hyphen = discretionary hyphen,
comment|//                                 U+00AD ISOnum
block|{
literal|"174"
block|,
literal|"reg"
block|,
literal|"{\\textregistered}"
block|}
block|,
comment|// registered sign = registered trade mark sign,
comment|//                                 U+00AE ISOnum
block|{
literal|"175"
block|,
literal|"macr"
block|,
literal|"{\\={}}"
block|}
block|,
comment|// macron = spacing macron = overline
comment|//                                 = APL overbar, U+00AF ISOdia
block|{
literal|"176"
block|,
literal|"deg"
block|,
literal|"{$^{\\circ}$}"
block|}
block|,
comment|// degree sign, U+00B0 ISOnum
block|{
literal|"177"
block|,
literal|"plusmn"
block|,
literal|"$\\pm$"
block|}
block|,
comment|// plus-minus sign = plus-or-minus sign,
comment|//                                 U+00B1 ISOnum
block|{
literal|"178"
block|,
literal|"sup2"
block|,
literal|"\\textsuperscript{2}"
block|}
block|,
comment|// superscript two = superscript digit two
comment|//                                 = squared, U+00B2 ISOnum
block|{
literal|"179"
block|,
literal|"sup3"
block|,
literal|"\\textsuperscript{3}"
block|}
block|,
comment|// superscript three = superscript digit three
comment|//                                 = cubed, U+00B3 ISOnum
block|{
literal|"180"
block|,
literal|"acute"
block|,
literal|"{\\'{}}"
block|}
block|,
comment|// acute accent = spacing acute,
comment|//                                 U+00B4 ISOdia
block|{
literal|"181"
block|,
literal|"micro"
block|,
literal|"$\\mu$"
block|}
block|,
comment|// micro sign, U+00B5 ISOnum
block|{
literal|""
block|,
literal|"mu"
block|,
literal|"$\\mu$"
block|}
block|,
comment|// micro sign, U+00B5 ISOnum
block|{
literal|"182"
block|,
literal|"para"
block|,
literal|"{{\\P}}"
block|}
block|,
comment|// pilcrow sign = paragraph sign,
comment|//                                 U+00B6 ISOnum
block|{
literal|"183"
block|,
literal|"middot"
block|,
literal|"$\\cdot$"
block|}
block|,
comment|// middle dot = Georgian comma
comment|//                                 = Greek middle dot, U+00B7 ISOnum
block|{
literal|"184"
block|,
literal|"cedil"
block|,
literal|"{\\c{}}"
block|}
block|,
comment|// cedilla = spacing cedilla, U+00B8 ISOdia
block|{
literal|"185"
block|,
literal|"sup1"
block|,
literal|"\\textsuperscript{1}"
block|}
block|,
comment|// superscript one = superscript digit one,
comment|//                                 U+00B9 ISOnum
block|{
literal|"186"
block|,
literal|"ordm"
block|,
literal|"{\\textordmasculine}"
block|}
block|,
comment|// masculine ordinal indicator,
comment|//                                 U+00BA ISOnum
block|{
literal|"187"
block|,
literal|"raquo"
block|,
literal|"{\\guillemotright}"
block|}
block|,
comment|// right-pointing double angle quotation mark
comment|//                                 = right pointing guillemet, U+00BB ISOnum
block|{
literal|"188"
block|,
literal|"frac14"
block|,
literal|"$\\sfrac{1}{4}$"
block|}
block|,
comment|// vulgar fraction one quarter
comment|//                                 = fraction one quarter, U+00BC ISOnum
block|{
literal|"189"
block|,
literal|"frac12"
block|,
literal|"$\\sfrac{1}{2}$"
block|}
block|,
comment|// vulgar fraction one half
comment|//                                 = fraction one half, U+00BD ISOnum
block|{
literal|"190"
block|,
literal|"frac34"
block|,
literal|"$\\sfrac{3}{4}$"
block|}
block|,
comment|// vulgar fraction three quarters
comment|//                                 = fraction three quarters, U+00BE ISOnum
block|{
literal|"191"
block|,
literal|"iquest"
block|,
literal|"{\\textquestiondown}"
block|}
block|,
comment|// inverted question mark
comment|//                                 = turned question mark, U+00BF ISOnum
block|{
literal|"192"
block|,
literal|"Agrave"
block|,
literal|"{{\\`{A}}}"
block|}
block|,
comment|// latin capital letter A with grave
comment|//                                 = latin capital letter A grave,
comment|//                                 U+00C0 ISOlat1
block|{
literal|"193"
block|,
literal|"Aacute"
block|,
literal|"{{\\'{A}}}"
block|}
block|,
comment|// latin capital letter A with acute,
comment|//                                 U+00C1 ISOlat1
block|{
literal|"194"
block|,
literal|"Acirc"
block|,
literal|"{{\\^{A}}}"
block|}
block|,
comment|// latin capital letter A with circumflex,
comment|//                                 U+00C2 ISOlat1
block|{
literal|"195"
block|,
literal|"Atilde"
block|,
literal|"{{\\~{A}}}"
block|}
block|,
comment|// latin capital letter A with tilde,
comment|//                                 U+00C3 ISOlat1
block|{
literal|"196"
block|,
literal|"Auml"
block|,
literal|"{{\\\"{A}}}"
block|}
block|,
comment|// latin capital letter A with diaeresis,
comment|//                                 U+00C4 ISOlat1
block|{
literal|"197"
block|,
literal|"Aring"
block|,
literal|"{{\\AA}}"
block|}
block|,
comment|// latin capital letter A with ring above
comment|//                                 = latin capital letter A ring,
comment|//                                 U+00C5 ISOlat1
block|{
literal|"198"
block|,
literal|"AElig"
block|,
literal|"{{\\AE}}"
block|}
block|,
comment|// latin capital letter AE
comment|//                                 = latin capital ligature AE,
comment|//                                 U+00C6 ISOlat1
block|{
literal|"199"
block|,
literal|"Ccedil"
block|,
literal|"{{\\c{C}}}"
block|}
block|,
comment|// latin capital letter C with cedilla,
comment|//                                 U+00C7 ISOlat1
block|{
literal|"200"
block|,
literal|"Egrave"
block|,
literal|"{{\\`{E}}}"
block|}
block|,
comment|// latin capital letter E with grave,
comment|//                                 U+00C8 ISOlat1
block|{
literal|"201"
block|,
literal|"Eacute"
block|,
literal|"{{\\'{E}}}"
block|}
block|,
comment|// latin capital letter E with acute,
comment|//                                 U+00C9 ISOlat1
block|{
literal|"202"
block|,
literal|"Ecirc"
block|,
literal|"{{\\^{E}}}"
block|}
block|,
comment|// latin capital letter E with circumflex,
comment|//                                 U+00CA ISOlat1
block|{
literal|"203"
block|,
literal|"Euml"
block|,
literal|"{{\\\"{E}}}"
block|}
block|,
comment|// latin capital letter E with diaeresis,
comment|//                                 U+00CB ISOlat1
block|{
literal|"204"
block|,
literal|"Igrave"
block|,
literal|"{{\\`{I}}}"
block|}
block|,
comment|// latin capital letter I with grave,
comment|//                                 U+00CC ISOlat1
block|{
literal|"205"
block|,
literal|"Iacute"
block|,
literal|"{{\\'{I}}}"
block|}
block|,
comment|// latin capital letter I with acute,
comment|//                                 U+00CD ISOlat1
block|{
literal|"206"
block|,
literal|"Icirc"
block|,
literal|"{{\\^{I}}}"
block|}
block|,
comment|// latin capital letter I with circumflex,
comment|//                                 U+00CE ISOlat1
block|{
literal|"207"
block|,
literal|"Iuml"
block|,
literal|"{{\\\"{I}}}"
block|}
block|,
comment|// latin capital letter I with diaeresis,
comment|//                                 U+00CF ISOlat1
block|{
literal|"208"
block|,
literal|"ETH"
block|,
literal|"{{\\DH}}"
block|}
block|,
comment|// latin capital letter ETH, U+00D0 ISOlat1
block|{
literal|"209"
block|,
literal|"Ntilde"
block|,
literal|"{{\\~{N}}}"
block|}
block|,
comment|// latin capital letter N with tilde,
comment|//                                 U+00D1 ISOlat1
block|{
literal|"210"
block|,
literal|"Ograve"
block|,
literal|"{{\\`{O}}}"
block|}
block|,
comment|// latin capital letter O with grave,
comment|//                                 U+00D2 ISOlat1
block|{
literal|"211"
block|,
literal|"Oacute"
block|,
literal|"{{\\'{O}}}"
block|}
block|,
comment|// latin capital letter O with acute,
comment|//                                 U+00D3 ISOlat1
block|{
literal|"212"
block|,
literal|"Ocirc"
block|,
literal|"{{\\^{O}}}"
block|}
block|,
comment|// latin capital letter O with circumflex,
comment|//                                 U+00D4 ISOlat1
block|{
literal|"213"
block|,
literal|"Otilde"
block|,
literal|"{{\\~{O}}}"
block|}
block|,
comment|// latin capital letter O with tilde,
comment|//                                 U+00D5 ISOlat1
block|{
literal|"214"
block|,
literal|"Ouml"
block|,
literal|"{{\\\"{O}}}"
block|}
block|,
comment|// latin capital letter O with diaeresis,
comment|//                                 U+00D6 ISOlat1
block|{
literal|"215"
block|,
literal|"times"
block|,
literal|"$\\times$"
block|}
block|,
comment|// multiplication sign, U+00D7 ISOnum
block|{
literal|"216"
block|,
literal|"Oslash"
block|,
literal|"{{\\O}}"
block|}
block|,
comment|// latin capital letter O with stroke
comment|//                                 = latin capital letter O slash,
comment|//                                 U+00D8 ISOlat1
block|{
literal|"217"
block|,
literal|"Ugrave"
block|,
literal|"{{\\`{U}}}"
block|}
block|,
comment|// latin capital letter U with grave,
comment|//                                 U+00D9 ISOlat1
block|{
literal|"218"
block|,
literal|"Uacute"
block|,
literal|"{{\\'{U}}}"
block|}
block|,
comment|// latin capital letter U with acute,
comment|//                                 U+00DA ISOlat1
block|{
literal|"219"
block|,
literal|"Ucirc"
block|,
literal|"{{\\^{U}}}"
block|}
block|,
comment|// latin capital letter U with circumflex,
comment|//                                 U+00DB ISOlat1
block|{
literal|"220"
block|,
literal|"Uuml"
block|,
literal|"{{\\\"{U}}}"
block|}
block|,
comment|// latin capital letter U with diaeresis,
comment|//                                 U+00DC ISOlat1
block|{
literal|"221"
block|,
literal|"Yacute"
block|,
literal|"{{\\'{Y}}}"
block|}
block|,
comment|// latin capital letter Y with acute,
comment|//                                 U+00DD ISOlat1
block|{
literal|"222"
block|,
literal|"THORN"
block|,
literal|"{{\\TH}}"
block|}
block|,
comment|// latin capital letter THORN,
comment|//                                 U+00DE ISOlat1
block|{
literal|"223"
block|,
literal|"szlig"
block|,
literal|"{\\ss}"
block|}
block|,
comment|// latin small letter sharp s = ess-zed,
comment|//                                 U+00DF ISOlat1
block|{
literal|"224"
block|,
literal|"agrave"
block|,
literal|"{\\`{a}}"
block|}
block|,
comment|// latin small letter a with grave
comment|//                                 = latin small letter a grave,
comment|//                                 U+00E0 ISOlat1
block|{
literal|"225"
block|,
literal|"aacute"
block|,
literal|"{\\'{a}}"
block|}
block|,
comment|// latin small letter a with acute,
comment|//                                 U+00E1 ISOlat1
block|{
literal|"226"
block|,
literal|"acirc"
block|,
literal|"{\\^{a}}"
block|}
block|,
comment|// latin small letter a with circumflex,
comment|//                                 U+00E2 ISOlat1
block|{
literal|"227"
block|,
literal|"atilde"
block|,
literal|"{\\~{a}}"
block|}
block|,
comment|// latin small letter a with tilde,
comment|//                                 U+00E3 ISOlat1
block|{
literal|"228"
block|,
literal|"auml"
block|,
literal|"{\\\"{a}}"
block|}
block|,
comment|// latin small letter a with diaeresis,
comment|//                                 U+00E4 ISOlat1
block|{
literal|"229"
block|,
literal|"aring"
block|,
literal|"{{\\aa}}"
block|}
block|,
comment|// latin small letter a with ring above
comment|//                                 = latin small letter a ring,
comment|//                                 U+00E5 ISOlat1
block|{
literal|"230"
block|,
literal|"aelig"
block|,
literal|"{\\ae}"
block|}
block|,
comment|// latin small letter ae
comment|//                                 = latin small ligature ae, U+00E6 ISOlat1
block|{
literal|"231"
block|,
literal|"ccedil"
block|,
literal|"{\\c{c}}"
block|}
block|,
comment|// latin small letter c with cedilla,
comment|//                                 U+00E7 ISOlat1
block|{
literal|"232"
block|,
literal|"egrave"
block|,
literal|"{\\`{e}}"
block|}
block|,
comment|// latin small letter e with grave,
comment|//                                 U+00E8 ISOlat1
block|{
literal|"233"
block|,
literal|"eacute"
block|,
literal|"{\\'{e}}"
block|}
block|,
comment|// latin small letter e with acute,
comment|//                                 U+00E9 ISOlat1
block|{
literal|"234"
block|,
literal|"ecirc"
block|,
literal|"{\\^{e}}"
block|}
block|,
comment|// latin small letter e with circumflex,
comment|//                                 U+00EA ISOlat1
block|{
literal|"235"
block|,
literal|"euml"
block|,
literal|"{\\\"{e}}"
block|}
block|,
comment|// latin small letter e with diaeresis,
comment|//                                 U+00EB ISOlat1
block|{
literal|"236"
block|,
literal|"igrave"
block|,
literal|"{\\`{i}}"
block|}
block|,
comment|// latin small letter i with grave,
comment|//                                 U+00EC ISOlat1
block|{
literal|"237"
block|,
literal|"iacute"
block|,
literal|"{\\'{i}}"
block|}
block|,
comment|// latin small letter i with acute,
comment|//                                 U+00ED ISOlat1
block|{
literal|"238"
block|,
literal|"icirc"
block|,
literal|"{\\^{i}}"
block|}
block|,
comment|// latin small letter i with circumflex,
comment|//                                 U+00EE ISOlat1
block|{
literal|"239"
block|,
literal|"iuml"
block|,
literal|"{\\\"{i}}"
block|}
block|,
comment|// latin small letter i with diaeresis,
comment|//                                 U+00EF ISOlat1
block|{
literal|"240"
block|,
literal|"eth"
block|,
literal|"{\\dh}"
block|}
block|,
comment|// latin small letter eth, U+00F0 ISOlat1
block|{
literal|"241"
block|,
literal|"ntilde"
block|,
literal|"{\\~{n}}"
block|}
block|,
comment|// latin small letter n with tilde,
comment|//                                 U+00F1 ISOlat1
block|{
literal|"242"
block|,
literal|"ograve"
block|,
literal|"{\\`{o}}"
block|}
block|,
comment|// latin small letter o with grave,
comment|//                                 U+00F2 ISOlat1
block|{
literal|"243"
block|,
literal|"oacute"
block|,
literal|"{\\'{o}}"
block|}
block|,
comment|// latin small letter o with acute,
comment|//                                 U+00F3 ISOlat1
block|{
literal|"244"
block|,
literal|"ocirc"
block|,
literal|"{\\^{o}}"
block|}
block|,
comment|// latin small letter o with circumflex,
comment|//                                 U+00F4 ISOlat1
block|{
literal|"245"
block|,
literal|"otilde"
block|,
literal|"{\\~{o}}"
block|}
block|,
comment|// latin small letter o with tilde,
comment|//                                 U+00F5 ISOlat1
block|{
literal|"246"
block|,
literal|"ouml"
block|,
literal|"{\\\"{o}}"
block|}
block|,
comment|// latin small letter o with diaeresis,
comment|//                                 U+00F6 ISOlat1
block|{
literal|"247"
block|,
literal|"divide"
block|,
literal|"$\\div$"
block|}
block|,
comment|// division sign, U+00F7 ISOnum
block|{
literal|"248"
block|,
literal|"oslash"
block|,
literal|"{\\o}"
block|}
block|,
comment|// latin small letter o with stroke,
comment|//                                 = latin small letter o slash,
comment|//                                 U+00F8 ISOlat1
block|{
literal|"249"
block|,
literal|"ugrave"
block|,
literal|"{\\`{u}}"
block|}
block|,
comment|// latin small letter u with grave,
comment|//                                 U+00F9 ISOlat1
block|{
literal|"250"
block|,
literal|"uacute"
block|,
literal|"{\\'{u}}"
block|}
block|,
comment|// latin small letter u with acute,
comment|//                                 U+00FA ISOlat1
block|{
literal|"251"
block|,
literal|"ucirc"
block|,
literal|"{\\^{u}}"
block|}
block|,
comment|// latin small letter u with circumflex,
comment|//                                 U+00FB ISOlat1
block|{
literal|"252"
block|,
literal|"uuml"
block|,
literal|"{\\\"{u}}"
block|}
block|,
comment|// latin small letter u with diaeresis,
comment|//                                 U+00FC ISOlat1
block|{
literal|"253"
block|,
literal|"yacute"
block|,
literal|"{\\'{y}}"
block|}
block|,
comment|// latin small letter y with acute,
comment|//                                 U+00FD ISOlat1
block|{
literal|"254"
block|,
literal|"thorn"
block|,
literal|"{\\th}"
block|}
block|,
comment|// latin small letter thorn,
comment|//                                 U+00FE ISOlat1
block|{
literal|"255"
block|,
literal|"yuml"
block|,
literal|"{\\\"{y}}"
block|}
block|,
comment|// latin small letter y with diaeresis,
comment|//                                 U+00FF ISOlat1
comment|/* Greek */
block|{
literal|"913"
block|,
literal|"Alpha"
block|,
literal|"{{$\\Alpha$}}"
block|}
block|,
comment|// greek capital letter alpha, U+0391
block|{
literal|"914"
block|,
literal|"Beta"
block|,
literal|"{{$\\Beta$}}"
block|}
block|,
comment|// greek capital letter beta, U+0392
block|{
literal|"915"
block|,
literal|"Gamma"
block|,
literal|"{{$\\Gamma$}}"
block|}
block|,
comment|// greek capital letter gamma,
comment|//                                   U+0393 ISOgrk3
block|{
literal|"916"
block|,
literal|"Delta"
block|,
literal|"{{$\\Delta$}}"
block|}
block|,
comment|// greek capital letter delta,
comment|//                                   U+0394 ISOgrk3
block|{
literal|"917"
block|,
literal|"Epsilon"
block|,
literal|"{{$\\Epsilon$}}"
block|}
block|,
comment|// greek capital letter epsilon, U+0395
block|{
literal|"918"
block|,
literal|"Zeta"
block|,
literal|"{{$\\Zeta$}}"
block|}
block|,
comment|// greek capital letter zeta, U+0396
block|{
literal|"919"
block|,
literal|"Eta"
block|,
literal|"{{$\\Eta$}}"
block|}
block|,
comment|// greek capital letter eta, U+0397
block|{
literal|"920"
block|,
literal|"Theta"
block|,
literal|"{{$\\Theta$}}"
block|}
block|,
comment|// greek capital letter theta,
comment|//                                   U+0398 ISOgrk3
block|{
literal|"921"
block|,
literal|"Iota"
block|,
literal|"{{$\\Iota$}}"
block|}
block|,
comment|// greek capital letter iota, U+0399
block|{
literal|"922"
block|,
literal|"Kappa"
block|,
literal|"{{$\\Kappa$}}"
block|}
block|,
comment|// greek capital letter kappa, U+039A
block|{
literal|"923"
block|,
literal|"Lambda"
block|,
literal|"{{$\\Lambda$}}"
block|}
block|,
comment|// greek capital letter lambda,
comment|//                                   U+039B ISOgrk3
block|{
literal|"924"
block|,
literal|"Mu"
block|,
literal|"{{$\\Mu$}}"
block|}
block|,
comment|// greek capital letter mu, U+039C
block|{
literal|"925"
block|,
literal|"Nu"
block|,
literal|"{{$\\Nu$}}"
block|}
block|,
comment|// greek capital letter nu, U+039D
block|{
literal|"926"
block|,
literal|"Xi"
block|,
literal|"{{$\\Xi$}}"
block|}
block|,
comment|// greek capital letter xi, U+039E ISOgrk3
block|{
literal|"927"
block|,
literal|"Omicron"
block|,
literal|"{{$\\Omicron$}}"
block|}
block|,
comment|// greek capital letter omicron, U+039F
block|{
literal|"928"
block|,
literal|"Pi"
block|,
literal|"{{$\\Pi$}}"
block|}
block|,
comment|// greek capital letter pi, U+03A0 ISOgrk3
block|{
literal|"929"
block|,
literal|"Rho"
block|,
literal|"{{$\\Rho$}}"
block|}
block|,
comment|// greek capital letter rho, U+03A1
comment|/* there is no Sigmaf, and no U+03A2 character either */
block|{
literal|"931"
block|,
literal|"Sigma"
block|,
literal|"{{$\\Sigma$}}"
block|}
block|,
comment|// greek capital letter sigma,
comment|//                                   U+03A3 ISOgrk3
block|{
literal|"932"
block|,
literal|"Tau"
block|,
literal|"{{$\\Tau$}}"
block|}
block|,
comment|// greek capital letter tau, U+03A4
block|{
literal|"933"
block|,
literal|"Upsilon"
block|,
literal|"{{$\\Upsilon$}}"
block|}
block|,
comment|// greek capital letter upsilon,
comment|//                                   U+03A5 ISOgrk3
block|{
literal|"934"
block|,
literal|"Phi"
block|,
literal|"{{$\\Phi$}}"
block|}
block|,
comment|// greek capital letter phi,
comment|//                                   U+03A6 ISOgrk3
block|{
literal|"935"
block|,
literal|"Chi"
block|,
literal|"{{$\\Chi$}}"
block|}
block|,
comment|// greek capital letter chi, U+03A7
block|{
literal|"936"
block|,
literal|"Psi"
block|,
literal|"{{$\\Psi$}}"
block|}
block|,
comment|// greek capital letter psi,
comment|//                                   U+03A8 ISOgrk3
block|{
literal|"937"
block|,
literal|"Omega"
block|,
literal|"{{$\\Omega$}}"
block|}
block|,
comment|// greek capital letter omega,
comment|//                                   U+03A9 ISOgrk3
block|{
literal|"945"
block|,
literal|"alpha"
block|,
literal|"$\\alpha$"
block|}
block|,
comment|// greek small letter alpha,
comment|//                                   U+03B1 ISOgrk3
block|{
literal|"946"
block|,
literal|"beta"
block|,
literal|"$\\beta$"
block|}
block|,
comment|// greek small letter beta, U+03B2 ISOgrk3
block|{
literal|"947"
block|,
literal|"gamma"
block|,
literal|"$\\gamma$"
block|}
block|,
comment|// greek small letter gamma,
comment|//                                   U+03B3 ISOgrk3
block|{
literal|"948"
block|,
literal|"delta"
block|,
literal|"$\\delta$"
block|}
block|,
comment|// greek small letter delta,
comment|//                                   U+03B4 ISOgrk3
block|{
literal|"949"
block|,
literal|"epsilon"
block|,
literal|"$\\epsilon$"
block|}
block|,
comment|// greek small letter epsilon,
comment|//                                   U+03B5 ISOgrk3
block|{
literal|"950"
block|,
literal|"zeta"
block|,
literal|"$\\zeta$"
block|}
block|,
comment|// greek small letter zeta, U+03B6 ISOgrk3
block|{
literal|"951"
block|,
literal|"eta"
block|,
literal|"$\\eta$"
block|}
block|,
comment|// greek small letter eta, U+03B7 ISOgrk3
block|{
literal|"952"
block|,
literal|"theta"
block|,
literal|"$\\theta$"
block|}
block|,
comment|// greek small letter theta,
comment|//                                   U+03B8 ISOgrk3
block|{
literal|"953"
block|,
literal|"iota"
block|,
literal|"$\\iota$"
block|}
block|,
comment|// greek small letter iota, U+03B9 ISOgrk3
block|{
literal|"954"
block|,
literal|"kappa"
block|,
literal|"$\\kappa$"
block|}
block|,
comment|// greek small letter kappa,
comment|//                                   U+03BA ISOgrk3
block|{
literal|"955"
block|,
literal|"lambda"
block|,
literal|"$\\lambda$"
block|}
block|,
comment|// greek small letter lambda,
comment|//                                   U+03BB ISOgrk3
block|{
literal|"956"
block|,
literal|"mu"
block|,
literal|"$\\mu$"
block|}
block|,
comment|// greek small letter mu, U+03BC ISOgrk3
block|{
literal|"957"
block|,
literal|"nu"
block|,
literal|"$\\nu$"
block|}
block|,
comment|// greek small letter nu, U+03BD ISOgrk3
block|{
literal|"958"
block|,
literal|"xi"
block|,
literal|"$\\xi$"
block|}
block|,
comment|// greek small letter xi, U+03BE ISOgrk3
block|{
literal|"959"
block|,
literal|"omicron"
block|,
literal|"$\\omicron$"
block|}
block|,
comment|// greek small letter omicron, U+03BF NEW
block|{
literal|"960"
block|,
literal|"pi"
block|,
literal|"$\\phi$"
block|}
block|,
comment|// greek small letter pi, U+03C0 ISOgrk3
block|{
literal|"961"
block|,
literal|"rho"
block|,
literal|"$\\rho$"
block|}
block|,
comment|// greek small letter rho, U+03C1 ISOgrk3
block|{
literal|"962"
block|,
literal|"sigmaf"
block|,
literal|"$\\varsigma$"
block|}
block|,
comment|// greek small letter final sigma,
comment|//                                   U+03C2 ISOgrk3
block|{
literal|"963"
block|,
literal|"sigma"
block|,
literal|"$\\sigma$"
block|}
block|,
comment|// greek small letter sigma,
comment|//                                   U+03C3 ISOgrk3
block|{
literal|"964"
block|,
literal|"tau"
block|,
literal|"$\\tau$"
block|}
block|,
comment|// greek small letter tau, U+03C4 ISOgrk3
block|{
literal|"965"
block|,
literal|"upsilon"
block|,
literal|"$\\upsilon$"
block|}
block|,
comment|// greek small letter upsilon,
block|{
literal|""
block|,
literal|"upsi"
block|,
literal|"$\\upsilon$"
block|}
block|,
comment|// alias
comment|//                                   U+03C5 ISOgrk3
block|{
literal|"966"
block|,
literal|"phi"
block|,
literal|"$\\phi$"
block|}
block|,
comment|// greek small letter phi, U+03C6 ISOgrk3
block|{
literal|"967"
block|,
literal|"chi"
block|,
literal|"$\\chi$"
block|}
block|,
comment|// greek small letter chi, U+03C7 ISOgrk3
block|{
literal|"968"
block|,
literal|"psi"
block|,
literal|"$\\psi$"
block|}
block|,
comment|// greek small letter psi, U+03C8 ISOgrk3
block|{
literal|"969"
block|,
literal|"omega"
block|,
literal|"$\\omega$"
block|}
block|,
comment|// greek small letter omega,
comment|//                                   U+03C9 ISOgrk3
block|{
literal|"977"
block|,
literal|"thetasym"
block|,
literal|"$\\vartheta$"
block|}
block|,
comment|// greek small letter theta symbol,
block|{
literal|""
block|,
literal|"thetav"
block|,
literal|"$\\vartheta$"
block|}
block|,
comment|// greek small letter theta symbol,
block|{
literal|""
block|,
literal|"vartheta"
block|,
literal|"$\\vartheta$"
block|}
block|,
comment|// greek small letter theta symbol,
comment|//                                   U+03D1 NEW
block|{
literal|"978"
block|,
literal|"upsih"
block|,
literal|"{{$\\Upsilon$}}"
block|}
block|,
comment|// greek upsilon with hook symbol,
comment|//                                   U+03D2 NEW
block|{
literal|"982"
block|,
literal|"piv"
block|,
literal|"$\\varphi$"
block|}
block|,
comment|// greek pi symbol, U+03D6 ISOgrk3
comment|/* General Punctuation */
block|{
literal|"8211"
block|,
literal|"ndash"
block|,
literal|"$\\textendash$"
block|}
block|,
block|{
literal|"8212"
block|,
literal|"mdash"
block|,
literal|"$\\textemdash$"
block|}
block|,
block|{
literal|"8226"
block|,
literal|"bull"
block|,
literal|"$\\bullet$"
block|}
block|,
comment|// bullet = black small circle,
comment|//                                    U+2022 ISOpub
comment|/* bullet is NOT the same as bullet operator, U+2219 */
block|{
literal|"8230"
block|,
literal|"hellip"
block|,
literal|"{\\ldots}"
block|}
block|,
comment|// horizontal ellipsis = three dot leader,
comment|//                                    U+2026 ISOpub
block|{
literal|"8242"
block|,
literal|"prime"
block|,
literal|"$\\prime$"
block|}
block|,
comment|// prime = minutes = feet, U+2032 ISOtech
block|{
literal|"8243"
block|,
literal|"Prime"
block|,
literal|"$\\prime\\prime$"
block|}
block|,
comment|// double prime = seconds = inches,
comment|//                                    U+2033 ISOtech
block|{
literal|"8254"
block|,
literal|"oline"
block|,
literal|"{\\={}}"
block|}
block|,
comment|// overline = spacing overscore,
comment|//                                    U+203E NEW
block|{
literal|"8260"
block|,
literal|"frasl"
block|,
literal|"/"
block|}
block|,
comment|// fraction slash, U+2044 NEW
comment|/* Letterlike Symbols */
block|{
literal|"8472"
block|,
literal|"weierp"
block|,
literal|"$\\wp$"
block|}
block|,
comment|// script capital P = power set
comment|//                                    = Weierstrass p, U+2118 ISOamso
block|{
literal|"8465"
block|,
literal|"image"
block|,
literal|"{{$\\Im$}}"
block|}
block|,
comment|// blackletter capital I = imaginary part,
comment|//                                    U+2111 ISOamso
block|{
literal|"8476"
block|,
literal|"real"
block|,
literal|"{{$\\Re$}}"
block|}
block|,
comment|// blackletter capital R = real part symbol,
comment|//                                    U+211C ISOamso
block|{
literal|"8482"
block|,
literal|"trade"
block|,
literal|"{\\texttrademark}"
block|}
block|,
comment|// trade mark sign, U+2122 ISOnum
block|{
literal|"8501"
block|,
literal|"alefsym"
block|,
literal|"$\\aleph$"
block|}
block|,
comment|// alef symbol = first transfinite cardinal,
comment|//                                    U+2135 NEW
comment|/*    alef symbol is NOT the same as hebrew letter alef,              U+05D0 although the same glyph could be used to depict both characters */
comment|/* Arrows */
block|{
literal|"8592"
block|,
literal|"larr"
block|,
literal|"$\\leftarrow$"
block|}
block|,
comment|// leftwards arrow, U+2190 ISOnum
block|{
literal|"8593"
block|,
literal|"uarr"
block|,
literal|"$\\uparrow$"
block|}
block|,
comment|// upwards arrow, U+2191 ISOnum
block|{
literal|"8594"
block|,
literal|"rarr"
block|,
literal|"$\\rightarrow$"
block|}
block|,
comment|// rightwards arrow, U+2192 ISOnum
block|{
literal|"8595"
block|,
literal|"darr"
block|,
literal|"$\\downarrow$"
block|}
block|,
comment|// downwards arrow, U+2193 ISOnum
block|{
literal|"8596"
block|,
literal|"harr"
block|,
literal|"$\\leftrightarrow$"
block|}
block|,
comment|// left right arrow, U+2194 ISOamsa
block|{
literal|"8629"
block|,
literal|"crarr"
block|,
literal|"$\\dlsh$"
block|}
block|,
comment|// downwards arrow with corner leftwards
comment|//                                    = carriage return, U+21B5 NEW - require mathabx
block|{
literal|"8656"
block|,
literal|"lArr"
block|,
literal|"{{$\\Leftarrow$}}"
block|}
block|,
comment|// leftwards double arrow, U+21D0 ISOtech
comment|/*  ISO 10646 does not say that lArr is the same as the 'is implied by' arrow              but also does not have any other character for that function. So ? lArr can              be used for 'is implied by' as ISOtech suggests */
block|{
literal|"8657"
block|,
literal|"uArr"
block|,
literal|"{{$\\Uparrow$}}"
block|}
block|,
comment|// upwards double arrow, U+21D1 ISOamsa
block|{
literal|"8658"
block|,
literal|"rArr"
block|,
literal|"{{$\\Rightarrow$}}"
block|}
block|,
comment|// rightwards double arrow,
comment|//                                     U+21D2 ISOtech
comment|/*   ISO 10646 does not say this is the 'implies' character but does not have              another character with this function so ?              rArr can be used for 'implies' as ISOtech suggests */
block|{
literal|"8659"
block|,
literal|"dArr"
block|,
literal|"{{$\\Downarrow$}}"
block|}
block|,
comment|// downwards double arrow, U+21D3 ISOamsa
block|{
literal|"8660"
block|,
literal|"hArr"
block|,
literal|"{{$\\Leftrightarrow$}}"
block|}
block|,
comment|// left right double arrow,
comment|//                                     U+21D4 ISOamsa
comment|/* Mathematical Operators */
block|{
literal|"8704"
block|,
literal|"forall"
block|,
literal|"$\\forall$"
block|}
block|,
comment|// for all, U+2200 ISOtech
block|{
literal|"8706"
block|,
literal|"part"
block|,
literal|"$\\partial$"
block|}
block|,
comment|// partial differential, U+2202 ISOtech
block|{
literal|"8707"
block|,
literal|"exist"
block|,
literal|"$\\exists$"
block|}
block|,
comment|// there exists, U+2203 ISOtech
block|{
literal|"8709"
block|,
literal|"empty"
block|,
literal|"$\\emptyset$"
block|}
block|,
comment|// empty set = null set = diameter,
comment|//                                    U+2205 ISOamso
block|{
literal|"8711"
block|,
literal|"nabla"
block|,
literal|"$\\nabla$"
block|}
block|,
comment|// nabla = backward difference,
comment|//                                    U+2207 ISOtech
block|{
literal|"8712"
block|,
literal|"isin"
block|,
literal|"$\\in$"
block|}
block|,
comment|// element of, U+2208 ISOtech
block|{
literal|"8713"
block|,
literal|"notin"
block|,
literal|"$\\notin$"
block|}
block|,
comment|// not an element of, U+2209 ISOtech
block|{
literal|"8715"
block|,
literal|"ni"
block|,
literal|"$\\ni$"
block|}
block|,
comment|// contains as member, U+220B ISOtech
comment|/* should there be a more memorable name than 'ni'? */
block|{
literal|"8719"
block|,
literal|"prod"
block|,
literal|"$\\prod$"
block|}
block|,
comment|// n-ary product = product sign,
comment|//                                    U+220F ISOamsb
comment|/*    prod is NOT the same character as U+03A0 'greek capital letter pi' though              the same glyph might be used for both  */
block|{
literal|"8721"
block|,
literal|"sum"
block|,
literal|"$\\sum$"
block|}
block|,
comment|// n-ary sumation, U+2211 ISOamsb
comment|/*    sum is NOT the same character as U+03A3 'greek capital letter sigma'              though the same glyph might be used for both */
block|{
literal|"8722"
block|,
literal|"minus"
block|,
literal|"$-$"
block|}
block|,
comment|// minus sign, U+2212 ISOtech
block|{
literal|"8727"
block|,
literal|"lowast"
block|,
literal|"$\\ast$"
block|}
block|,
comment|// asterisk operator, U+2217 ISOtech
block|{
literal|"8730"
block|,
literal|"radic"
block|,
literal|"$\\sqrt{}$"
block|}
block|,
comment|// square root = radical sign,
comment|//                                    U+221A ISOtech
block|{
literal|"8733"
block|,
literal|"prop"
block|,
literal|"$\\propto$"
block|}
block|,
comment|// proportional to, U+221D ISOtech
block|{
literal|"8734"
block|,
literal|"infin"
block|,
literal|"$\\infty$"
block|}
block|,
comment|// infinity, U+221E ISOtech
block|{
literal|"8736"
block|,
literal|"ang"
block|,
literal|"$\\angle$"
block|}
block|,
comment|// angle, U+2220 ISOamso
block|{
literal|"8743"
block|,
literal|"and"
block|,
literal|"$\\land$"
block|}
block|,
comment|// logical and = wedge, U+2227 ISOtech
block|{
literal|"8744"
block|,
literal|"or"
block|,
literal|"$\\lor$"
block|}
block|,
comment|// logical or = vee, U+2228 ISOtech
block|{
literal|"8745"
block|,
literal|"cap"
block|,
literal|"$\\cap$"
block|}
block|,
comment|// intersection = cap, U+2229 ISOtech
block|{
literal|"8746"
block|,
literal|"cup"
block|,
literal|"$\\cup$"
block|}
block|,
comment|// union = cup, U+222A ISOtech
block|{
literal|"8747"
block|,
literal|"int"
block|,
literal|"$\\int$"
block|}
block|,
comment|// integral, U+222B ISOtech
block|{
literal|"8756"
block|,
literal|"there4"
block|,
literal|"$\\therefore$"
block|}
block|,
comment|// therefore, U+2234 ISOtech; AMSSymb
block|{
literal|"8764"
block|,
literal|"sim"
block|,
literal|"$\\sim$"
block|}
block|,
comment|// tilde operator = varies with = similar to,
comment|//                                    U+223C ISOtech
comment|/*  tilde operator is NOT the same character as the tilde, U+007E,              although the same glyph might be used to represent both   */
block|{
literal|"8773"
block|,
literal|"cong"
block|,
literal|"$\\cong$"
block|}
block|,
comment|// approximately equal to, U+2245 ISOtech
block|{
literal|"8776"
block|,
literal|"asymp"
block|,
literal|"$\\approx$"
block|}
block|,
comment|// almost equal to = asymptotic to,
comment|//                                    U+2248 ISOamsr
block|{
literal|"8800"
block|,
literal|"ne"
block|,
literal|"$\\neq$"
block|}
block|,
comment|// not equal to, U+2260 ISOtech
block|{
literal|"8801"
block|,
literal|"equiv"
block|,
literal|"$\\equiv$"
block|}
block|,
comment|// identical to, U+2261 ISOtech
block|{
literal|"8804"
block|,
literal|"le"
block|,
literal|"$\\leq$"
block|}
block|,
comment|// less-than or equal to, U+2264 ISOtech
block|{
literal|"8805"
block|,
literal|"ge"
block|,
literal|"$\\geq$"
block|}
block|,
comment|// greater-than or equal to,
comment|//                                    U+2265 ISOtech
block|{
literal|"8834"
block|,
literal|"sub"
block|,
literal|"$\\subset$"
block|}
block|,
comment|// subset of, U+2282 ISOtech
block|{
literal|"8835"
block|,
literal|"sup"
block|,
literal|"$\\supset$"
block|}
block|,
comment|// superset of, U+2283 ISOtech
comment|/*    note that nsup, 'not a superset of, U+2283' is not covered by the Symbol              font encoding and is not included. Should it be, for symmetry?              It is in ISOamsn   */
block|{
literal|"8836"
block|,
literal|"nsub"
block|,
literal|"$\\not\\subset$"
block|}
block|,
comment|// not a subset of, U+2284 ISOamsn
block|{
literal|"8838"
block|,
literal|"sube"
block|,
literal|"$\\subseteq$"
block|}
block|,
comment|// subset of or equal to, U+2286 ISOtech
block|{
literal|"8839"
block|,
literal|"supe"
block|,
literal|"$\\supseteq$"
block|}
block|,
comment|// superset of or equal to,
comment|//                                    U+2287 ISOtech
block|{
literal|"8853"
block|,
literal|"oplus"
block|,
literal|"$\\oplus$"
block|}
block|,
comment|// circled plus = direct sum,
comment|//                                    U+2295 ISOamsb
block|{
literal|"8855"
block|,
literal|"otimes"
block|,
literal|"$\\otimes$"
block|}
block|,
comment|// circled times = vector product,
comment|//                                    U+2297 ISOamsb
block|{
literal|"8869"
block|,
literal|"perp"
block|,
literal|"$\\perp$"
block|}
block|,
comment|// up tack = orthogonal to = perpendicular,
comment|//                                    U+22A5 ISOtech
block|{
literal|"8901"
block|,
literal|"sdot"
block|,
literal|"$\\cdot$"
block|}
block|,
comment|// dot operator, U+22C5 ISOamsb
comment|/* dot operator is NOT the same character as U+00B7 middle dot */
block|{
literal|"8968"
block|,
literal|"lceil"
block|,
literal|"$\\lceil$"
block|}
block|,
comment|// left ceiling = apl upstile,
comment|//                                    U+2308 ISOamsc
block|{
literal|"8969"
block|,
literal|"rceil"
block|,
literal|"$\\rceil$"
block|}
block|,
comment|// right ceiling, U+2309 ISOamsc
block|{
literal|"8970"
block|,
literal|"lfloor"
block|,
literal|"$\\lfloor$"
block|}
block|,
comment|// left floor = apl downstile,
comment|//                                    U+230A ISOamsc
block|{
literal|"8971"
block|,
literal|"rfloor"
block|,
literal|"$\\rfloor$"
block|}
block|,
comment|// right floor, U+230B ISOamsc
comment|/* Miscellaneous Technical */
block|{
literal|"9001"
block|,
literal|"lang"
block|,
literal|"$\\langle$"
block|}
block|,
comment|// left-pointing angle bracket = bra,
comment|//                                    U+2329 ISOtech
comment|/*    lang is NOT the same character as U+003C 'less than'              or U+2039 'single left-pointing angle quotation mark' */
block|{
literal|"9002"
block|,
literal|"rang"
block|,
literal|"$\\rangle$"
block|}
block|,
comment|// right-pointing angle bracket = ket,
comment|//                                    U+232A ISOtech
comment|/*    rang is NOT the same character as U+003E 'greater than'              or U+203A 'single right-pointing angle quotation mark' */
comment|/* Geometric Shapes */
block|{
literal|"9674"
block|,
literal|"loz"
block|,
literal|"$\\lozenge$"
block|}
block|,
comment|// lozenge, U+25CA ISOpub
comment|/* Miscellaneous Symbols */
block|{
literal|"9824"
block|,
literal|"spades"
block|,
literal|"$\\spadesuit$"
block|}
block|,
comment|// black spade suit, U+2660 ISOpub
comment|/* black here seems to mean filled as opposed to hollow */
block|{
literal|"9827"
block|,
literal|"clubs"
block|,
literal|"$\\clubsuit$"
block|}
block|,
comment|// black club suit = shamrock,
comment|//                                    U+2663 ISOpub
block|{
literal|"9829"
block|,
literal|"hearts"
block|,
literal|"$\\heartsuit$"
block|}
block|,
comment|// black heart suit = valentine,
comment|//                                    U+2665 ISOpub
block|{
literal|"9830"
block|,
literal|"diams"
block|,
literal|"$\\diamondsuit$"
block|}
block|,
comment|// black diamond suit, U+2666 ISOpub
block|{
literal|"34"
block|,
literal|"quot"
block|,
literal|"\""
block|}
block|,
comment|// quotation mark = APL quote,
comment|//                                   U+0022 ISOnum
block|{
literal|"38"
block|,
literal|"amp"
block|,
literal|"\\&"
block|}
block|,
comment|// ampersand, U+0026 ISOnum
block|{
literal|"60"
block|,
literal|"lt"
block|,
literal|"$<$"
block|}
block|,
comment|// less-than sign, U+003C ISOnum
block|{
literal|"62"
block|,
literal|"gt"
block|,
literal|"$>$"
block|}
block|,
comment|// greater-than sign, U+003E ISOnum
comment|/* General Punctuation */
block|{
literal|"8194"
block|,
literal|"ensp"
block|,
literal|"\\hspace{0.5em}"
block|}
block|,
comment|// en space, U+2002 ISOpub
block|{
literal|"8195"
block|,
literal|"emsp"
block|,
literal|"\\hspace{1em}"
block|}
block|,
comment|// em space, U+2003 ISOpub
block|{
literal|"8201"
block|,
literal|"thinsp"
block|,
literal|"\\hspace{0.167em}"
block|}
block|,
comment|// thin space, U+2009 ISOpub
block|{
literal|"8202"
block|,
literal|""
block|,
literal|"\\hspace{0.1em}"
block|}
block|,
comment|// hair space, U+2010 ISOpub
block|{
literal|"8204"
block|,
literal|"zwnj"
block|,
literal|"\\/{}"
block|}
block|,
comment|// zero width non-joiner,
comment|//                                   U+200C NEW RFC 2070
block|{
literal|"8205"
block|,
literal|"zwj"
block|,
literal|""
block|}
block|,
comment|// zero width joiner, U+200D NEW RFC 2070
block|{
literal|"8206"
block|,
literal|"lrm"
block|,
literal|""
block|}
block|,
comment|// left-to-right mark, U+200E NEW RFC 2070
block|{
literal|"8207"
block|,
literal|"rlm"
block|,
literal|""
block|}
block|,
comment|// right-to-left mark, U+200F NEW RFC 2070
block|{
literal|"8211"
block|,
literal|"ndash"
block|,
literal|"--"
block|}
block|,
comment|// en dash, U+2013 ISOpub
block|{
literal|"8212"
block|,
literal|"mdash"
block|,
literal|"---"
block|}
block|,
comment|// em dash, U+2014 ISOpub
block|{
literal|"8216"
block|,
literal|"lsquo"
block|,
literal|"{\\textquoteleft}"
block|}
block|,
comment|// left single quotation mark,
comment|//                                   U+2018 ISOnum
block|{
literal|"8217"
block|,
literal|"rsquo"
block|,
literal|"{\\textquoteright}"
block|}
block|,
comment|// right single quotation mark,
comment|//                                   U+2019 ISOnum
block|{
literal|"8218"
block|,
literal|"sbquo"
block|,
literal|"{\\quotesinglbase}"
block|}
block|,
comment|// single low-9 quotation mark, U+201A NEW
block|{
literal|"8220"
block|,
literal|"ldquo"
block|,
literal|"{\\textquotedblleft}"
block|}
block|,
comment|// left double quotation mark,
comment|//                                   U+201C ISOnum
block|{
literal|"8221"
block|,
literal|"rdquo"
block|,
literal|"{\\textquotedblright}"
block|}
block|,
comment|// right double quotation mark,
comment|//                                   U+201D ISOnum
block|{
literal|"8222"
block|,
literal|"bdquo"
block|,
literal|"{\\quotedblbase}"
block|}
block|,
comment|// double low-9 quotation mark, U+201E NEW
block|{
literal|"8224"
block|,
literal|"dagger"
block|,
literal|"{\\dag}"
block|}
block|,
comment|// dagger, U+2020 ISOpub
block|{
literal|"8225"
block|,
literal|"Dagger"
block|,
literal|"{\\ddag}"
block|}
block|,
comment|// double dagger, U+2021 ISOpub
block|{
literal|"8240"
block|,
literal|"permil"
block|,
literal|"{\\textperthousand}"
block|}
block|,
comment|// per mille sign, U+2030 ISOtech
block|{
literal|"8249"
block|,
literal|"lsaquo"
block|,
literal|"{\\guilsinglleft}"
block|}
block|,
comment|// single left-pointing angle quotation mark,
comment|//                                   U+2039 ISO proposed
comment|/* lsaquo is proposed but not yet ISO standardized */
block|{
literal|"8250"
block|,
literal|"rsaquo"
block|,
literal|"{\\guilsinglright}"
block|}
block|,
comment|// single right-pointing angle quotation mark,
comment|//                                   U+203A ISO proposed
comment|/* rsaquo is proposed but not yet ISO standardized */
block|{
literal|"8364"
block|,
literal|"euro"
block|,
literal|"{\\texteuro}"
block|}
block|,
comment|// euro sign, U+20AC NEW
comment|/* Manually added */
block|{
literal|"35"
block|,
literal|""
block|,
literal|"\\#"
block|}
block|,
comment|// Hash
block|{
literal|"36"
block|,
literal|"dollar"
block|,
literal|"\\$"
block|}
block|,
comment|// Dollar
block|{
literal|"37"
block|,
literal|"#37"
block|,
literal|"\\%"
block|}
block|,
comment|// Percent (&percnt; is not displayed correctly in the entry preview)
block|{
literal|"39"
block|,
literal|"apos"
block|,
literal|"'"
block|}
block|,
comment|// Apostrophe
block|{
literal|"40"
block|,
literal|"lpar"
block|,
literal|"("
block|}
block|,
comment|// Left bracket
block|{
literal|"41"
block|,
literal|"rpar"
block|,
literal|")"
block|}
block|,
comment|// Right bracket
block|{
literal|"42"
block|,
literal|""
block|,
literal|"*"
block|}
block|,
comment|// Asterisk
block|{
literal|"43"
block|,
literal|"plus"
block|,
literal|"+"
block|}
block|,
comment|// Plus
block|{
literal|"44"
block|,
literal|"comma"
block|,
literal|","
block|}
block|,
comment|// Comma
block|{
literal|"45"
block|,
literal|"hyphen"
block|,
literal|"-"
block|}
block|,
comment|// Hyphen
block|{
literal|"46"
block|,
literal|"period"
block|,
literal|"."
block|}
block|,
comment|// Period
block|{
literal|"47"
block|,
literal|"slash"
block|,
literal|"/"
block|}
block|,
comment|// Slash (solidus)
block|{
literal|"58"
block|,
literal|"colon"
block|,
literal|":"
block|}
block|,
comment|// Colon
block|{
literal|"59"
block|,
literal|"semi"
block|,
literal|";"
block|}
block|,
comment|// Semi colon
block|{
literal|"61"
block|,
literal|"equals"
block|,
literal|"="
block|}
block|,
comment|// Equals to
block|{
literal|"91"
block|,
literal|"lsqb"
block|,
literal|"["
block|}
block|,
comment|// Left square bracket
block|{
literal|"92"
block|,
literal|"bsol"
block|,
literal|"{\\textbackslash}"
block|}
block|,
comment|// Backslash
block|{
literal|"93"
block|,
literal|"rsqb"
block|,
literal|"]"
block|}
block|,
comment|// Right square bracket
block|{
literal|"94"
block|,
literal|"Hat"
block|,
literal|"{\\^{}}"
block|}
block|,
comment|// Circumflex
block|{
literal|"95"
block|,
literal|"lowbar"
block|,
literal|"\\_"
block|}
block|,
comment|// Underscore
block|{
literal|"96"
block|,
literal|"grave"
block|,
literal|"{\\`{}}"
block|}
block|,
comment|// Grave
block|{
literal|"123"
block|,
literal|"lbrace"
block|,
literal|"\\{"
block|}
block|,
comment|// Left curly bracket
block|{
literal|""
block|,
literal|"lcub"
block|,
literal|"\\{"
block|}
block|,
comment|// Left curly bracket
block|{
literal|"124"
block|,
literal|"vert"
block|,
literal|"|"
block|}
block|,
comment|// Vertical bar
block|{
literal|""
block|,
literal|"verbar"
block|,
literal|"|"
block|}
block|,
comment|// Vertical bar
block|{
literal|""
block|,
literal|"VerticalLine"
block|,
literal|"|"
block|}
block|,
comment|// Vertical bar
block|{
literal|"125"
block|,
literal|"rbrace"
block|,
literal|"\\}"
block|}
block|,
comment|// Right curly bracket
block|{
literal|""
block|,
literal|"rcub"
block|,
literal|"\\}"
block|}
block|,
comment|// Right curly bracket
comment|// {"138", "", "{{\\v{S}}}"}, // Line tabulation set
comment|// {"141", "", ""}, // Reverse line feed
block|{
literal|"145"
block|,
literal|""
block|,
literal|"`"
block|}
block|,
comment|// Apostrophe
block|{
literal|"146"
block|,
literal|""
block|,
literal|"'"
block|}
block|,
comment|// Apostrophe
block|{
literal|"147"
block|,
literal|""
block|,
literal|"``"
block|}
block|,
comment|// Quotation mark
block|{
literal|"148"
block|,
literal|""
block|,
literal|"''"
block|}
block|,
comment|// Quotation mark
block|{
literal|"150"
block|,
literal|""
block|,
literal|"--"
block|}
block|,
comment|// En dash
comment|// {"154", "", "{\\v{s}}"}, // Single character introducer
block|{
literal|"256"
block|,
literal|""
block|,
literal|"{{\\={A}}}"
block|}
block|,
comment|// capital A with macron
block|{
literal|"257"
block|,
literal|""
block|,
literal|"{\\={a}}"
block|}
block|,
comment|// small a with macron
block|{
literal|"258"
block|,
literal|""
block|,
literal|"{{\\u{A}}}"
block|}
block|,
comment|// capital A with breve
block|{
literal|"259"
block|,
literal|""
block|,
literal|"{\\u{a}}"
block|}
block|,
comment|// small a with breve
block|{
literal|"260"
block|,
literal|"Aogon"
block|,
literal|"{{\\k{A}}}"
block|}
block|,
comment|// capital A with ogonek
block|{
literal|"261"
block|,
literal|"aogon"
block|,
literal|"{\\k{a}}"
block|}
block|,
comment|// small a with ogonek
block|{
literal|"262"
block|,
literal|"Cacute"
block|,
literal|"{{\\'{C}}}"
block|}
block|,
comment|// capital C with acute
block|{
literal|"263"
block|,
literal|"cacute"
block|,
literal|"{\\'{c}}"
block|}
block|,
comment|// small C with acute
block|{
literal|"264"
block|,
literal|"Ccirc"
block|,
literal|"{{\\^{C}}}"
block|}
block|,
comment|// capital C with circumflex
block|{
literal|"265"
block|,
literal|"ccirc"
block|,
literal|"{\\^{c}}"
block|}
block|,
comment|// small C with circumflex
block|{
literal|"266"
block|,
literal|"Cdot"
block|,
literal|"{{\\.{C}}}"
block|}
block|,
comment|// capital C with dot above
block|{
literal|"267"
block|,
literal|"cdot"
block|,
literal|"{\\.{c}}"
block|}
block|,
comment|// small C with dot above
block|{
literal|"268"
block|,
literal|"Ccaron"
block|,
literal|"{{\\v{C}}}"
block|}
block|,
comment|// capital C with caron
block|{
literal|"269"
block|,
literal|"ccaron"
block|,
literal|"{\\v{c}}"
block|}
block|,
comment|// small C with caron
block|{
literal|"270"
block|,
literal|""
block|,
literal|"{{\\v{D}}}"
block|}
block|,
comment|// capital D with caron
block|{
literal|"271"
block|,
literal|""
block|,
literal|"{\\v{d}}"
block|}
block|,
comment|// small d with caron
block|{
literal|"272"
block|,
literal|"Dstrok"
block|,
literal|"{{\\DJ}}"
block|}
block|,
comment|// capital D with stroke
block|{
literal|"273"
block|,
literal|"dstrok"
block|,
literal|"{{\\dj}}"
block|}
block|,
comment|// small d with stroke
block|{
literal|"274"
block|,
literal|""
block|,
literal|"{{\\={E}}}"
block|}
block|,
comment|// capital E with macron
block|{
literal|"275"
block|,
literal|""
block|,
literal|"{\\={e}}"
block|}
block|,
comment|// small e with macron
block|{
literal|"276"
block|,
literal|""
block|,
literal|"{{\\u{E}}}"
block|}
block|,
comment|// capital E with breve
block|{
literal|"277"
block|,
literal|""
block|,
literal|"{\\u{e}}"
block|}
block|,
comment|// small e with breve
block|{
literal|"278"
block|,
literal|""
block|,
literal|"{{\\.{E}}}"
block|}
block|,
comment|// capital E with dot above
block|{
literal|"279"
block|,
literal|""
block|,
literal|"{\\.{e}}"
block|}
block|,
comment|// small e with dot above
block|{
literal|"280"
block|,
literal|"Eogon"
block|,
literal|"{{\\k{E}}}"
block|}
block|,
comment|// capital E with ogonek
block|{
literal|"281"
block|,
literal|"eogon"
block|,
literal|"{\\k{e}}"
block|}
block|,
comment|// small e with ogonek
block|{
literal|"282"
block|,
literal|""
block|,
literal|"{{\\v{E}}}"
block|}
block|,
comment|// capital E with caron
block|{
literal|"283"
block|,
literal|""
block|,
literal|"{\\v{e}}"
block|}
block|,
comment|// small e with caron
block|{
literal|"284"
block|,
literal|""
block|,
literal|"{{\\^{G}}}"
block|}
block|,
comment|// capital G with circumflex
block|{
literal|"285"
block|,
literal|""
block|,
literal|"{\\^{g}}"
block|}
block|,
comment|// small g with circumflex
block|{
literal|"286"
block|,
literal|""
block|,
literal|"{{\\u{G}}}"
block|}
block|,
comment|// capital G with breve
block|{
literal|"287"
block|,
literal|""
block|,
literal|"{\\u{g}}"
block|}
block|,
comment|// small g with breve
block|{
literal|"288"
block|,
literal|""
block|,
literal|"{{\\.{G}}}"
block|}
block|,
comment|// capital G with dot above
block|{
literal|"289"
block|,
literal|""
block|,
literal|"{\\.{g}}"
block|}
block|,
comment|// small g with dot above
block|{
literal|"290"
block|,
literal|""
block|,
literal|"{{\\c{G}}}"
block|}
block|,
comment|// capital G with cedilla
block|{
literal|"291"
block|,
literal|""
block|,
literal|"{\\c{g}}"
block|}
block|,
comment|// small g with cedilla
block|{
literal|"292"
block|,
literal|""
block|,
literal|"{{\\^{H}}}"
block|}
block|,
comment|// capital H with circumflex
block|{
literal|"293"
block|,
literal|""
block|,
literal|"{\\^{h}}"
block|}
block|,
comment|// small h with circumflex
block|{
literal|"294"
block|,
literal|""
block|,
literal|"{{\\B{H}}}"
block|}
block|,
comment|// capital H with stroke
block|{
literal|"295"
block|,
literal|""
block|,
literal|"{\\B{h}}"
block|}
block|,
comment|// small h with stroke
block|{
literal|"296"
block|,
literal|""
block|,
literal|"{{\\~{I}}}"
block|}
block|,
comment|// capital I with tilde
block|{
literal|"297"
block|,
literal|""
block|,
literal|"{\\~{\\i}}"
block|}
block|,
comment|// small i with tilde
block|{
literal|"298"
block|,
literal|"Imacr"
block|,
literal|"{{\\={I}}}"
block|}
block|,
comment|// capital I with macron
block|{
literal|"299"
block|,
literal|"imacr"
block|,
literal|"{\\={\\i}}"
block|}
block|,
comment|// small i with macron
block|{
literal|"300"
block|,
literal|""
block|,
literal|"{{\\u{I}}}"
block|}
block|,
comment|// capital I with breve
block|{
literal|"301"
block|,
literal|""
block|,
literal|"{\\u{\\i}}"
block|}
block|,
comment|// small i with breve
block|{
literal|"302"
block|,
literal|"Iogon"
block|,
literal|"{{\\k{I}}}"
block|}
block|,
comment|// capital I with ogonek
block|{
literal|"303"
block|,
literal|"iogon"
block|,
literal|"{\\k{i}}"
block|}
block|,
comment|// small i with ogonek
block|{
literal|"304"
block|,
literal|"Idot"
block|,
literal|"{{\\.{I}}}"
block|}
block|,
comment|// capital I with dot above
block|{
literal|"305"
block|,
literal|"inodot"
block|,
literal|"{\\i}"
block|}
block|,
comment|// Small i without the dot
block|{
literal|""
block|,
literal|"imath"
block|,
literal|"{\\i}"
block|}
block|,
comment|// Small i without the dot
block|{
literal|"306"
block|,
literal|""
block|,
literal|"{{\\IJ}}"
block|}
block|,
comment|// Dutch di-graph IJ
block|{
literal|"307"
block|,
literal|""
block|,
literal|"{{\\ij}}"
block|}
block|,
comment|// Dutch di-graph ij
block|{
literal|"308"
block|,
literal|""
block|,
literal|"{{\\^{J}}}"
block|}
block|,
comment|// capital J with circumflex
block|{
literal|"309"
block|,
literal|""
block|,
literal|"{\\^{\\j}}"
block|}
block|,
comment|// small j with circumflex
block|{
literal|"310"
block|,
literal|""
block|,
literal|"{{\\c{K}}}"
block|}
block|,
comment|// capital K with cedilla
block|{
literal|"311"
block|,
literal|""
block|,
literal|"{\\c{k}}"
block|}
block|,
comment|// small k with cedilla
block|{
literal|"312"
block|,
literal|""
block|,
literal|"{\\textkra}"
block|}
block|,
comment|// Letter kra
block|{
literal|"313"
block|,
literal|""
block|,
literal|"{{\\'{L}}}"
block|}
block|,
comment|// capital L with acute
block|{
literal|"314"
block|,
literal|""
block|,
literal|"{\\'{l}}"
block|}
block|,
comment|// small l with acute
block|{
literal|"315"
block|,
literal|""
block|,
literal|"{{\\c{L}}}"
block|}
block|,
comment|// capital L with cedilla
block|{
literal|"316"
block|,
literal|""
block|,
literal|"{\\c{l}}"
block|}
block|,
comment|// small l with cedilla
block|{
literal|"317"
block|,
literal|""
block|,
literal|"{{\\v{L}}}"
block|}
block|,
comment|// capital L with caron
block|{
literal|"318"
block|,
literal|""
block|,
literal|"{\\v{l}}"
block|}
block|,
comment|// small l with caron
comment|//{"319", "Lmidot", "{\\Lmidot}"}, // upper case L with mid dot
comment|//{"320", "lmidot", "{\\lmidot}"}, // lower case l with mid dot
block|{
literal|"321"
block|,
literal|"Lstrok"
block|,
literal|"{{\\L}}"
block|}
block|,
comment|// upper case L with stroke
block|{
literal|"322"
block|,
literal|"lstrok"
block|,
literal|"{{\\l}}"
block|}
block|,
comment|// lower case l with stroke
block|{
literal|"323"
block|,
literal|"Nacute"
block|,
literal|"{{\\'{N}}}"
block|}
block|,
comment|// upper case N with acute
block|{
literal|"324"
block|,
literal|"nacute"
block|,
literal|"{{\\'{n}}}"
block|}
block|,
comment|// lower case n with acute
block|{
literal|"325"
block|,
literal|""
block|,
literal|"{{\\c{N}}}"
block|}
block|,
comment|// capital N with cedilla
block|{
literal|"326"
block|,
literal|""
block|,
literal|"{\\c{n}}"
block|}
block|,
comment|// small n with cedilla
block|{
literal|"327"
block|,
literal|""
block|,
literal|"{{\\v{N}}}"
block|}
block|,
comment|// capital N with caron
block|{
literal|"328"
block|,
literal|""
block|,
literal|"{\\v{n}}"
block|}
block|,
comment|// small n with caron
block|{
literal|"329"
block|,
literal|""
block|,
literal|"{'n}"
block|}
block|,
comment|// small n preceded with apostroph
block|{
literal|"330"
block|,
literal|""
block|,
literal|"{{\\NG}}"
block|}
block|,
comment|// upper case letter Eng
block|{
literal|"331"
block|,
literal|""
block|,
literal|"{{\\ng}}"
block|}
block|,
comment|// lower case letter Eng
block|{
literal|"332"
block|,
literal|"Omacro"
block|,
literal|"{{\\={O}}}"
block|}
block|,
comment|// the capital letter O with macron
block|{
literal|"333"
block|,
literal|"omacro"
block|,
literal|"{\\={o}}"
block|}
block|,
comment|// the small letter o with macron
block|{
literal|"334"
block|,
literal|""
block|,
literal|"{{\\u{O}}}"
block|}
block|,
comment|// the capital letter O with breve
block|{
literal|"335"
block|,
literal|""
block|,
literal|"{\\u{o}}"
block|}
block|,
comment|// the small letter o with breve
block|{
literal|"336"
block|,
literal|""
block|,
literal|"{{\\H{O}}}"
block|}
block|,
comment|// the capital letter O with double acute
block|{
literal|"337"
block|,
literal|""
block|,
literal|"{\\H{o}}"
block|}
block|,
comment|// the small letter o with double acute
block|{
literal|"338"
block|,
literal|"OElig"
block|,
literal|"{{\\OE}}"
block|}
block|,
comment|// OE-ligature
block|{
literal|"339"
block|,
literal|"oelig"
block|,
literal|"{{\\oe}}"
block|}
block|,
comment|// oe-ligature
block|{
literal|"340"
block|,
literal|""
block|,
literal|"{{\\'{R}}}"
block|}
block|,
comment|// upper case R with acute
block|{
literal|"341"
block|,
literal|""
block|,
literal|"{{\\'{r}}}"
block|}
block|,
comment|// lower case r with acute
block|{
literal|"342"
block|,
literal|""
block|,
literal|"{{\\c{R}}}"
block|}
block|,
comment|// upper case R with cedilla
block|{
literal|"343"
block|,
literal|""
block|,
literal|"{{\\c{r}}}"
block|}
block|,
comment|// lower case r with cedilla
block|{
literal|"344"
block|,
literal|""
block|,
literal|"{{\\v{R}}}"
block|}
block|,
comment|// upper case R with caron
block|{
literal|"345"
block|,
literal|""
block|,
literal|"{{\\v{r}}}"
block|}
block|,
comment|// lower case r with caron
block|{
literal|"346"
block|,
literal|""
block|,
literal|"{{\\'{S}}}"
block|}
block|,
comment|// upper case S with acute
block|{
literal|"347"
block|,
literal|""
block|,
literal|"{{\\'{s}}}"
block|}
block|,
comment|// lower case s with acute
block|{
literal|"348"
block|,
literal|"Scirc"
block|,
literal|"{{\\^{S}}}"
block|}
block|,
comment|// upper case S with circumflex
block|{
literal|"349"
block|,
literal|"scirc"
block|,
literal|"{\\^{s}}"
block|}
block|,
comment|// lower case s with circumflex
block|{
literal|"350"
block|,
literal|"Scedil"
block|,
literal|"{{\\c{S}}}"
block|}
block|,
comment|// upper case S with cedilla
block|{
literal|"351"
block|,
literal|"scedil"
block|,
literal|"{\\c{s}}"
block|}
block|,
comment|// lower case s with cedilla
block|{
literal|"352"
block|,
literal|"Scaron"
block|,
literal|"{{\\v{S}}}"
block|}
block|,
comment|// latin capital letter S with caron,
block|{
literal|"353"
block|,
literal|"scaron"
block|,
literal|"{\\v{s}}"
block|}
block|,
comment|// latin small letter s with caron,
block|{
literal|"354"
block|,
literal|""
block|,
literal|"{{\\c{T}}}"
block|}
block|,
comment|// upper case T with cedilla
block|{
literal|"355"
block|,
literal|""
block|,
literal|"{{\\c{T}}}"
block|}
block|,
comment|// lower case t with cedilla
block|{
literal|"356"
block|,
literal|""
block|,
literal|"{{\\v{T}}}"
block|}
block|,
comment|// latin capital letter T with caron,
block|{
literal|"357"
block|,
literal|""
block|,
literal|"{\\v{t}}"
block|}
block|,
comment|// latin small letter t with caron,
block|{
literal|"358"
block|,
literal|""
block|,
literal|"{{\\B{T}}}"
block|}
block|,
comment|// latin capital letter T with stroke,
block|{
literal|"359"
block|,
literal|""
block|,
literal|"{\\B{t}}"
block|}
block|,
comment|// latin small letter t with stroke,
block|{
literal|"360"
block|,
literal|""
block|,
literal|"{{\\~{U}}}"
block|}
block|,
comment|// capital U with tilde
block|{
literal|"361"
block|,
literal|""
block|,
literal|"{\\~{u}}"
block|}
block|,
comment|// small u with tilde
block|{
literal|"362"
block|,
literal|""
block|,
literal|"{{\\={U}}}"
block|}
block|,
comment|// capital U with macron
block|{
literal|"363"
block|,
literal|""
block|,
literal|"{\\={u}}"
block|}
block|,
comment|// small u with macron
block|{
literal|"364"
block|,
literal|""
block|,
literal|"{{\\u{U}}}"
block|}
block|,
comment|// capital U with breve
block|{
literal|"365"
block|,
literal|""
block|,
literal|"{\\u{u}}"
block|}
block|,
comment|// small u with breve
block|{
literal|"366"
block|,
literal|""
block|,
literal|"{{\\r{U}}}"
block|}
block|,
comment|// capital U with ring
block|{
literal|"367"
block|,
literal|""
block|,
literal|"{\\r{u}}"
block|}
block|,
comment|// small u with ring
block|{
literal|"368"
block|,
literal|""
block|,
literal|"{{\\={U}}}"
block|}
block|,
comment|// capital U with double acute
block|{
literal|"369"
block|,
literal|""
block|,
literal|"{\\={u}}"
block|}
block|,
comment|// small u with double acute
block|{
literal|"370"
block|,
literal|"Uogon"
block|,
literal|"{{\\k{U}}}"
block|}
block|,
comment|// capital U with ogonek
block|{
literal|"371"
block|,
literal|"uogon"
block|,
literal|"{\\k{u}}"
block|}
block|,
comment|// small u with ogonek
block|{
literal|"372"
block|,
literal|""
block|,
literal|"{{\\^{W}}}"
block|}
block|,
comment|// capital W with circumflex
block|{
literal|"373"
block|,
literal|""
block|,
literal|"{\\^{w}}"
block|}
block|,
comment|// small w with circumflex
block|{
literal|"374"
block|,
literal|""
block|,
literal|"{{\\^{Y}}}"
block|}
block|,
comment|// capital Y with circumflex
block|{
literal|"375"
block|,
literal|""
block|,
literal|"{\\^{y}}"
block|}
block|,
comment|// small y with circumflex
block|{
literal|"376"
block|,
literal|"Yuml"
block|,
literal|"{{\\\"{Y}}}"
block|}
block|,
comment|// latin capital letter Y with diaeresis,
block|{
literal|"377"
block|,
literal|""
block|,
literal|"{{\\'{Z}}}"
block|}
block|,
comment|// capital Z with acute
block|{
literal|"378"
block|,
literal|""
block|,
literal|"{\\'{z}}"
block|}
block|,
comment|// small z with acute
block|{
literal|"379"
block|,
literal|""
block|,
literal|"{{\\.{Z}}}"
block|}
block|,
comment|// capital Z with dot above
block|{
literal|"380"
block|,
literal|""
block|,
literal|"{\\.{z}}"
block|}
block|,
comment|// small z with dot above
block|{
literal|"381"
block|,
literal|"Zcaron"
block|,
literal|"{{\\v{Z}}}"
block|}
block|,
comment|// capital Z with caron
block|{
literal|"382"
block|,
literal|"zcaron"
block|,
literal|"{\\v{z}}"
block|}
block|,
comment|// small z with caron
comment|// {"383", "", ""}, // long s
block|{
literal|"384"
block|,
literal|""
block|,
literal|"{\\B{b}}"
block|}
block|,
comment|// small b with stroke
block|{
literal|"402"
block|,
literal|"fnof"
block|,
literal|"\\textit{f}"
block|}
block|,
comment|// latin small f with hook = function
block|{
literal|"405"
block|,
literal|""
block|,
literal|"{{\\hv}}"
block|}
block|,
comment|// small letter Hv
block|{
literal|"416"
block|,
literal|""
block|,
literal|"{{\\OHORN}}"
block|}
block|,
comment|// capital O with horn
block|{
literal|"417"
block|,
literal|""
block|,
literal|"{{\\ohorn}}"
block|}
block|,
comment|// small o with horn
block|{
literal|"431"
block|,
literal|""
block|,
literal|"{{\\UHORN}}"
block|}
block|,
comment|// capital U with horn
block|{
literal|"432"
block|,
literal|""
block|,
literal|"{{\\uhorn}}"
block|}
block|,
comment|// small u with horn
block|{
literal|"490"
block|,
literal|"Oogon"
block|,
literal|"{{\\k{O}}}"
block|}
block|,
comment|// capital letter O with ogonek
block|{
literal|"491"
block|,
literal|"oogon"
block|,
literal|"{\\k{o}}"
block|}
block|,
comment|// small letter o with ogonek
block|{
literal|"492"
block|,
literal|""
block|,
literal|"{{\\k{\\={O}}}}"
block|}
block|,
comment|// capital letter O with ogonek and macron
block|{
literal|"493"
block|,
literal|""
block|,
literal|"{\\k{\\={o}}}"
block|}
block|,
comment|// small letter o with ogonek and macron
block|{
literal|"536"
block|,
literal|""
block|,
literal|"{{\\cb{S}}}"
block|}
block|,
comment|// capital letter S with comma below, require combelow
block|{
literal|"537"
block|,
literal|""
block|,
literal|"{\\cb{s}}"
block|}
block|,
comment|// small letter S with comma below, require combelow
block|{
literal|"538"
block|,
literal|""
block|,
literal|"{{\\cb{T}}}"
block|}
block|,
comment|// capital letter T with comma below, require combelow
block|{
literal|"539"
block|,
literal|""
block|,
literal|"{\\cb{t}}"
block|}
block|,
comment|// small letter T with comma below, require combelow
block|{
literal|"710"
block|,
literal|"circ"
block|,
literal|"{\\^{}}"
block|}
block|,
comment|// modifier letter circumflex accent,
block|{
literal|"726"
block|,
literal|""
block|,
literal|"+"
block|}
block|,
comment|// Modifier plus sign
block|{
literal|"727"
block|,
literal|""
block|,
literal|"-"
block|}
block|,
comment|// Modifier minus sign
block|{
literal|"728"
block|,
literal|"breve"
block|,
literal|"{\\u{}}"
block|}
block|,
comment|// Breve
block|{
literal|""
block|,
literal|"Breve"
block|,
literal|"{\\u{}}"
block|}
block|,
comment|// Breve
block|{
literal|"729"
block|,
literal|"dot"
block|,
literal|"{\\.{}}"
block|}
block|,
comment|// Dot above
block|{
literal|"730"
block|,
literal|"ring"
block|,
literal|"{\\r{}}"
block|}
block|,
comment|// Ring above
block|{
literal|"731"
block|,
literal|"ogon"
block|,
literal|"{\\k{}}"
block|}
block|,
comment|// Ogonek
block|{
literal|"732"
block|,
literal|"tilde"
block|,
literal|"\\~{}"
block|}
block|,
comment|// Small tilde
block|{
literal|"733"
block|,
literal|"dblac"
block|,
literal|"{{\\H{}}}"
block|}
block|,
comment|// Double acute
block|{
literal|"949"
block|,
literal|"epsi"
block|,
literal|"$\\epsilon$"
block|}
block|,
comment|// Epsilon - double check
block|{
literal|"1013"
block|,
literal|"epsiv"
block|,
literal|"$\\varepsilonup$"
block|}
block|,
comment|// lunate epsilon, requires txfonts
comment|//{"1055", "", "{{\\cyrchar\\CYRP}}"}, // Cyrillic capital Pe
comment|//{"1082", "", "{\\cyrchar\\cyrk}"}, // Cyrillic small Ka
comment|// {"2013", "", ""},    // NKO letter FA -- Maybe en dash = 0x2013?
comment|// {"2014", "", ""},    // NKO letter FA -- Maybe em dash = 0x2014?
block|{
literal|"8192"
block|,
literal|""
block|,
literal|"\\hspace{0.5em}"
block|}
block|,
comment|// en quad
block|{
literal|"8193"
block|,
literal|""
block|,
literal|"\\hspace{1em}"
block|}
block|,
comment|// em quad
block|{
literal|"8196"
block|,
literal|""
block|,
literal|"\\hspace{0.333em}"
block|}
block|,
comment|// Three-Per-Em Space
block|{
literal|"8197"
block|,
literal|""
block|,
literal|"\\hspace{0.25em}"
block|}
block|,
comment|// Four-Per-Em Space
block|{
literal|"8198"
block|,
literal|""
block|,
literal|"\\hspace{0.167em}"
block|}
block|,
comment|// Six-Per-Em Space
block|{
literal|"8208"
block|,
literal|"hyphen"
block|,
literal|"-"
block|}
block|,
comment|// Hyphen
block|{
literal|"8229"
block|,
literal|"nldr"
block|,
literal|"\\.{}\\.{}"
block|}
block|,
comment|// Double dots - en leader
block|{
literal|"8241"
block|,
literal|""
block|,
literal|"{\\textpertenthousand}"
block|}
block|,
comment|// per ten thousands sign
block|{
literal|"8244"
block|,
literal|""
block|,
literal|"{$\\prime\\prime\\prime$}"
block|}
block|,
comment|// triple prime
block|{
literal|"8251"
block|,
literal|""
block|,
literal|"{\\textreferencemark}"
block|}
block|,
block|{
literal|"8253"
block|,
literal|""
block|,
literal|"{\\textinterrobang}"
block|}
block|,
block|{
literal|"8320"
block|,
literal|""
block|,
literal|"$_{0}$"
block|}
block|,
comment|// sub-script 0
block|{
literal|"8321"
block|,
literal|""
block|,
literal|"$_{1}$"
block|}
block|,
comment|// sub-script 1
block|{
literal|"8322"
block|,
literal|""
block|,
literal|"$_{2}$"
block|}
block|,
comment|// sub-script 2
block|{
literal|"8323"
block|,
literal|""
block|,
literal|"$_{3}$"
block|}
block|,
comment|// sub-script 3
block|{
literal|"8324"
block|,
literal|""
block|,
literal|"$_{4}$"
block|}
block|,
comment|// sub-script 4
block|{
literal|"8325"
block|,
literal|""
block|,
literal|"$_{5}$"
block|}
block|,
comment|// sub-script 5
block|{
literal|"8326"
block|,
literal|""
block|,
literal|"$_{6}$"
block|}
block|,
comment|// sub-script 6
block|{
literal|"8327"
block|,
literal|""
block|,
literal|"$_{7}$"
block|}
block|,
comment|// sub-script 7
block|{
literal|"8328"
block|,
literal|""
block|,
literal|"$_{8}$"
block|}
block|,
comment|// sub-script 8
block|{
literal|"8329"
block|,
literal|""
block|,
literal|"$_{9}$"
block|}
block|,
comment|// sub-script 9
block|{
literal|"8330"
block|,
literal|""
block|,
literal|"$_{+}$"
block|}
block|,
comment|// sub-script +
block|{
literal|"8331"
block|,
literal|""
block|,
literal|"$_{-}$"
block|}
block|,
comment|// sub-script -
block|{
literal|"8332"
block|,
literal|""
block|,
literal|"$_{-}$"
block|}
block|,
comment|// sub-script =
block|{
literal|"8333"
block|,
literal|""
block|,
literal|"$_{(}$"
block|}
block|,
comment|// sub-script (
block|{
literal|"8334"
block|,
literal|""
block|,
literal|"$_{)}$"
block|}
block|,
comment|// sub-script )
block|{
literal|"8450"
block|,
literal|"complexes"
block|,
literal|"$\\mathbb{C}$"
block|}
block|,
comment|// double struck capital C -- requires e.g. amsfonts
block|{
literal|"8451"
block|,
literal|""
block|,
literal|"{\\textcelsius}"
block|}
block|,
comment|// Degree Celsius
block|{
literal|"8459"
block|,
literal|"Hscr"
block|,
literal|"{{$\\mathcal{H}$}}"
block|}
block|,
comment|// script capital H -- possibly use \mathscr
block|{
literal|"8460"
block|,
literal|"Hfr"
block|,
literal|"{{$\\mathbb{H}$}}"
block|}
block|,
comment|// black letter capital H -- requires e.g. amsfonts
block|{
literal|"8466"
block|,
literal|"Lscr"
block|,
literal|"{{$\\mathcal{L}$}}"
block|}
block|,
comment|// script capital L -- possibly use \mathscr
block|{
literal|"8467"
block|,
literal|"ell"
block|,
literal|"{$\\ell$}"
block|}
block|,
comment|// script small l
block|{
literal|"8469"
block|,
literal|"naturals"
block|,
literal|"{{$\\mathbb{N}$}}"
block|}
block|,
comment|// double struck capital N -- requires e.g. amsfonts
block|{
literal|"8474"
block|,
literal|"Qopf"
block|,
literal|"{{$\\mathbb{Q}$}}"
block|}
block|,
comment|// double struck capital Q -- requires e.g. amsfonts
block|{
literal|"8477"
block|,
literal|"reals"
block|,
literal|"{{$\\mathbb{R}$}}"
block|}
block|,
comment|// double struck capital R -- requires e.g. amsfonts
block|{
literal|"8486"
block|,
literal|""
block|,
literal|"${{\\Omega}}$"
block|}
block|,
comment|// Omega
block|{
literal|"8491"
block|,
literal|"angst"
block|,
literal|"{{\\AA}}"
block|}
block|,
comment|// Angstrom
block|{
literal|"8496"
block|,
literal|"Escr"
block|,
literal|"{{$\\mathcal{E}$}}"
block|}
block|,
comment|// script capital E
block|{
literal|"8531"
block|,
literal|"frac13"
block|,
literal|"$\\sfrac{1}{3}$"
block|}
block|,
comment|// Vulgar fraction one third
block|{
literal|"8532"
block|,
literal|"frac23"
block|,
literal|"$\\sfrac{2}{3}$"
block|}
block|,
comment|// Vulgar fraction two thirds
block|{
literal|"8533"
block|,
literal|"frac15"
block|,
literal|"$\\sfrac{1}{5}$"
block|}
block|,
comment|// Vulgar fraction one fifth
block|{
literal|"8534"
block|,
literal|"frac25"
block|,
literal|"$\\sfrac{2}{5}$"
block|}
block|,
comment|// Vulgar fraction two fifths
block|{
literal|"8535"
block|,
literal|"frac35"
block|,
literal|"$\\sfrac{3}{5}$"
block|}
block|,
comment|// Vulgar fraction three fifths
block|{
literal|"8536"
block|,
literal|"frac45"
block|,
literal|"$\\sfrac{4}{5}$"
block|}
block|,
comment|// Vulgar fraction four fifths
block|{
literal|"8537"
block|,
literal|"frac16"
block|,
literal|"$\\sfrac{1}{6}$"
block|}
block|,
comment|// Vulgar fraction one sixth
block|{
literal|"8538"
block|,
literal|"frac56"
block|,
literal|"$\\sfrac{5}{6}$"
block|}
block|,
comment|// Vulgar fraction five sixths
block|{
literal|"8539"
block|,
literal|"frac18"
block|,
literal|"$\\sfrac{1}{8}$"
block|}
block|,
comment|// Vulgar fraction one eighth
block|{
literal|"8540"
block|,
literal|"frac38"
block|,
literal|"$\\sfrac{3}{8}$"
block|}
block|,
comment|// Vulgar fraction three eighths
block|{
literal|"8541"
block|,
literal|"frac58"
block|,
literal|"$\\sfrac{5}{8}$"
block|}
block|,
comment|// Vulgar fraction five eighths
block|{
literal|"8542"
block|,
literal|"frac78"
block|,
literal|"$\\sfrac{7}{8}$"
block|}
block|,
comment|// Vulgar fraction seven eighths
block|{
literal|"8710"
block|,
literal|""
block|,
literal|"$\\triangle$"
block|}
block|,
comment|// Increment - could use a more appropriate symbol
block|{
literal|"8714"
block|,
literal|""
block|,
literal|"$\\in$"
block|}
block|,
comment|// Small element in
block|{
literal|"8723"
block|,
literal|"mp"
block|,
literal|"$\\mp$"
block|}
block|,
comment|// Minus-plus
block|{
literal|"8729"
block|,
literal|"bullet"
block|,
literal|"$\\bullet$"
block|}
block|,
comment|// Bullet operator
block|{
literal|"8741"
block|,
literal|""
block|,
literal|"$\\parallel$"
block|}
block|,
comment|// Parallel to
block|{
literal|"8758"
block|,
literal|"ratio"
block|,
literal|":"
block|}
block|,
comment|// Colon/ratio
block|{
literal|"8771"
block|,
literal|"sime"
block|,
literal|"$\\simeq$"
block|}
block|,
comment|// almost equal to = asymptotic to,
block|{
literal|"8776"
block|,
literal|"ap"
block|,
literal|"$\\approx$"
block|}
block|,
comment|// almost equal to = asymptotic to,
block|{
literal|"8810"
block|,
literal|"ll"
block|,
literal|"$\\ll$"
block|}
block|,
comment|// Much less than
block|{
literal|""
block|,
literal|"Lt"
block|,
literal|"$\\ll$"
block|}
block|,
comment|// Much less than
block|{
literal|"8811"
block|,
literal|"gg"
block|,
literal|"$\\gg$"
block|}
block|,
comment|// Much greater than
block|{
literal|""
block|,
literal|"Gt"
block|,
literal|"$\\gg$"
block|}
block|,
comment|// Much greater than
block|{
literal|"8818"
block|,
literal|"lsim"
block|,
literal|"$\\lesssim$"
block|}
block|,
comment|// Less than or equivalent to
block|{
literal|"8819"
block|,
literal|"gsim"
block|,
literal|"$\\gtrsim$"
block|}
block|,
comment|// Greater than or equivalent to
block|{
literal|"8862"
block|,
literal|"boxplus"
block|,
literal|"$\\boxplus$"
block|}
block|,
comment|// Boxed plus -- requires amssymb
block|{
literal|"8863"
block|,
literal|"boxminus"
block|,
literal|"$\\boxminus$"
block|}
block|,
comment|// Boxed minus -- requires amssymb
block|{
literal|"8864"
block|,
literal|"boxtimes"
block|,
literal|"$\\boxtimes$"
block|}
block|,
comment|// Boxed times -- requires amssymb
block|{
literal|"8882"
block|,
literal|"vltri"
block|,
literal|"$\\triangleleft$"
block|}
block|,
comment|// Left triangle
block|{
literal|"8883"
block|,
literal|"vrtri"
block|,
literal|"$\\triangleright$"
block|}
block|,
comment|// Right triangle
block|{
literal|"8896"
block|,
literal|"xwedge"
block|,
literal|"$\\bigwedge$"
block|}
block|,
comment|// Big wedge
block|{
literal|"8897"
block|,
literal|"xvee"
block|,
literal|"$\\bigvee$"
block|}
block|,
comment|// Big vee
block|{
literal|"8942"
block|,
literal|"vdots"
block|,
literal|"$\\vdots$"
block|}
block|,
comment|// vertical ellipsis U+22EE
block|{
literal|"8943"
block|,
literal|"cdots"
block|,
literal|"$\\cdots$"
block|}
block|,
comment|// midline horizontal ellipsis U+22EF
comment|/*{"8944", "", "$\\ddots$"}, // up right diagonal ellipsis U+22F0 */
block|{
literal|"8945"
block|,
literal|"ddots"
block|,
literal|"$\\ddots$"
block|}
block|,
comment|// down right diagonal ellipsis U+22F1
block|{
literal|"9426"
block|,
literal|"circledc"
block|,
literal|"{\\copyright}"
block|}
block|,
comment|// circled small letter C
block|{
literal|"9633"
block|,
literal|"square"
block|,
literal|"$\\square$"
block|}
block|,
comment|// White square
block|{
literal|"9651"
block|,
literal|"xutri"
block|,
literal|"$\\bigtriangleup$"
block|}
block|,
comment|// White up-pointing big triangle
block|{
literal|"9653"
block|,
literal|"utri"
block|,
literal|"$\\triangle$"
block|}
block|,
comment|// White up-pointing small triangle -- \vartriangle probably
comment|// better but requires amssymb
block|{
literal|"10877"
block|,
literal|"les"
block|,
literal|"$\\leqslant$"
block|}
block|,
comment|// Less than slanted equal -- requires amssymb
block|{
literal|"10878"
block|,
literal|"ges"
block|,
literal|"$\\geqslant$"
block|}
block|,
comment|// Less than slanted equal -- requires amssymb
block|{
literal|"64256"
block|,
literal|""
block|,
literal|"ff"
block|}
block|,
comment|// ff ligature (which LaTeX solves by itself)
block|{
literal|"64257"
block|,
literal|""
block|,
literal|"fi"
block|}
block|,
comment|// fi ligature (which LaTeX solves by itself)
block|{
literal|"64258"
block|,
literal|""
block|,
literal|"fl"
block|}
block|,
comment|// fl ligature (which LaTeX solves by itself)
block|{
literal|"64259"
block|,
literal|""
block|,
literal|"ffi"
block|}
block|,
comment|// ffi ligature (which LaTeX solves by itself)
block|{
literal|"64260"
block|,
literal|""
block|,
literal|"ffl"
block|}
block|,
comment|// ffl ligature (which LaTeX solves by itself)
block|{
literal|"119978"
block|,
literal|"Oscr"
block|,
literal|"$\\mathcal{O}$"
block|}
block|,
comment|// script capital O -- possibly use \mathscr
block|{
literal|"119984"
block|,
literal|"Uscr"
block|,
literal|"$\\mathcal{U}$"
block|}
comment|// script capital U -- possibly use \mathscr
block|}
decl_stmt|;
comment|// List of combining accents
DECL|field|ACCENT_LIST
specifier|private
specifier|static
specifier|final
name|String
index|[]
index|[]
name|ACCENT_LIST
init|=
operator|new
name|String
index|[]
index|[]
block|{
block|{
literal|"768"
block|,
literal|"`"
block|}
block|,
comment|// Grave
block|{
literal|"769"
block|,
literal|"'"
block|}
block|,
comment|// Acute
block|{
literal|"770"
block|,
literal|"^"
block|}
block|,
comment|// Circumflex
block|{
literal|"771"
block|,
literal|"~"
block|}
block|,
comment|// Tilde
block|{
literal|"772"
block|,
literal|"="
block|}
block|,
comment|// Macron
block|{
literal|"773"
block|,
literal|"="
block|}
block|,
comment|// Overline - not completely correct
block|{
literal|"774"
block|,
literal|"u"
block|}
block|,
comment|// Breve
block|{
literal|"775"
block|,
literal|"."
block|}
block|,
comment|// Dot above
block|{
literal|"776"
block|,
literal|"\""
block|}
block|,
comment|// Diaeresis
block|{
literal|"777"
block|,
literal|"h"
block|}
block|,
comment|// Hook above
block|{
literal|"778"
block|,
literal|"r"
block|}
block|,
comment|// Ring
block|{
literal|"779"
block|,
literal|"H"
block|}
block|,
comment|// Double acute
block|{
literal|"780"
block|,
literal|"v"
block|}
block|,
comment|// Caron
block|{
literal|"781"
block|,
literal|"|"
block|}
block|,
comment|// Vertical line above
block|{
literal|"782"
block|,
literal|"U"
block|}
block|,
comment|// Double vertical line above
block|{
literal|"783"
block|,
literal|"G"
block|}
block|,
comment|// Double grave
block|{
literal|"784"
block|,
literal|"textdotbreve"
block|}
block|,
comment|// Candrabindu
block|{
literal|"785"
block|,
literal|"t"
block|}
block|,
comment|// Inverted breve
comment|//        {"786", ""},    // Turned comma above
comment|//        {"787", ""},    // Comma above
block|{
literal|"788"
block|,
literal|"textrevcommaabove"
block|}
block|,
comment|// Reversed comma above
block|{
literal|"789"
block|,
literal|"textcommaabover"
block|}
block|,
comment|// Comma above right
block|{
literal|"790"
block|,
literal|"textsubgrave"
block|}
block|,
comment|// Grave accent below -requires tipa
block|{
literal|"791"
block|,
literal|"textsubacute"
block|}
block|,
comment|// Acute accent below - requires tipa
block|{
literal|"792"
block|,
literal|"textadvancing"
block|}
block|,
comment|// Left tack below - requires tipa
block|{
literal|"793"
block|,
literal|"textretracting"
block|}
block|,
comment|// Right tack below - requires tipa
block|{
literal|"794"
block|,
literal|"textlangleabove"
block|}
block|,
comment|// Left angle above
block|{
literal|"795"
block|,
literal|"textrighthorn"
block|}
block|,
comment|// Horn
block|{
literal|"796"
block|,
literal|"textsublhalfring"
block|}
block|,
comment|// Left half ring below - requires tipa
block|{
literal|"797"
block|,
literal|"textraising"
block|}
block|,
comment|// Up tack below - requires tipa
block|{
literal|"798"
block|,
literal|"textlowering"
block|}
block|,
comment|// Down tack below - requires tipa
block|{
literal|"799"
block|,
literal|"textsubplus"
block|}
block|,
comment|// Plus sign below - requires tipa
block|{
literal|"800"
block|,
literal|"textsubbar"
block|}
block|,
comment|// Minus sign below
block|{
literal|"801"
block|,
literal|"textpalhookbelow"
block|}
block|,
comment|// Palatalized hook below
block|{
literal|"802"
block|,
literal|"M"
block|}
block|,
comment|// Retroflex hook below - textrethookbelow?
block|{
literal|"803"
block|,
literal|"d"
block|}
block|,
comment|// Dot below
block|{
literal|"804"
block|,
literal|"textsubumlaut"
block|}
block|,
comment|// Diaeresis below - requires tipa
block|{
literal|"805"
block|,
literal|"textsubring"
block|}
block|,
comment|// Ring below - requires tipa
block|{
literal|"806"
block|,
literal|"cb"
block|}
block|,
comment|// Comma below - requires combelow
block|{
literal|"807"
block|,
literal|"c"
block|}
block|,
comment|// Cedilla
block|{
literal|"808"
block|,
literal|"k"
block|}
block|,
comment|// Ogonek
block|{
literal|"809"
block|,
literal|"textsyllabic"
block|}
block|,
comment|// Vertical line below - requires tipa
block|{
literal|"810"
block|,
literal|"textsubbridge"
block|}
block|,
comment|// Bridge below - requires tipa
block|{
literal|"811"
block|,
literal|"textsubw"
block|}
block|,
comment|// Inverted double arch below - requires tipa
block|{
literal|"812"
block|,
literal|"textsubwedge"
block|}
block|,
comment|// Caron below
block|{
literal|"813"
block|,
literal|"textsubcircum"
block|}
block|,
comment|// Circumflex accent below - requires tipa
block|{
literal|"814"
block|,
literal|"textsubbreve"
block|}
block|,
comment|// Breve below
block|{
literal|"815"
block|,
literal|"textsubarch"
block|}
block|,
comment|// Inverted breve below - requires tipa
block|{
literal|"816"
block|,
literal|"textsubtilde"
block|}
block|,
comment|// Tilde below - requires tipa
block|{
literal|"817"
block|,
literal|"b"
block|}
block|,
comment|// Macron below - not completely correct
block|{
literal|"818"
block|,
literal|"b"
block|}
block|,
comment|// Underline
block|{
literal|"819"
block|,
literal|"subdoublebar"
block|}
block|,
comment|// Double low line -- requires extraipa
block|{
literal|"820"
block|,
literal|"textsuperimposetilde"
block|}
block|,
comment|// Tilde overlay - requires tipa
block|{
literal|"821"
block|,
literal|"B"
block|}
block|,
comment|// Short stroke overlay - textsstrokethru?
block|{
literal|"822"
block|,
literal|"textlstrokethru"
block|}
block|,
comment|// Long stroke overlay
block|{
literal|"823"
block|,
literal|"textsstrikethru"
block|}
block|,
comment|// Short solidus overlay
block|{
literal|"824"
block|,
literal|"textlstrikethru"
block|}
block|,
comment|// Long solidus overlay
block|{
literal|"825"
block|,
literal|"textsubrhalfring"
block|}
block|,
comment|// Right half ring below - requires tipa
block|{
literal|"826"
block|,
literal|"textinvsubbridge"
block|}
block|,
comment|// inverted bridge below - requires tipa
block|{
literal|"827"
block|,
literal|"textsubsquare"
block|}
block|,
comment|// Square below - requires tipa
block|{
literal|"828"
block|,
literal|"textseagull"
block|}
block|,
comment|// Seagull below - requires tipa
block|{
literal|"829"
block|,
literal|"textovercross"
block|}
block|,
comment|// X above - requires tipa
comment|//        {"830", ""},    // Vertical tilde
comment|//        {"831", ""},    // Double overline
comment|//        {"832", ""},    // Grave tone mark
comment|//        {"833", ""},    // Acute tone mark
comment|//        {"834", ""},    // Greek perispomeni
comment|//        {"835", ""},    // Greek koronis
comment|//        {"836", ""},    // Greek dialytika tonos
comment|//        {"837", ""},    // Greek ypogegrammeni
block|{
literal|"838"
block|,
literal|"overbridge"
block|}
block|,
comment|// Bridge above - requires extraipa
block|{
literal|"839"
block|,
literal|"subdoublebar"
block|}
block|,
comment|// Equals sign below - requires extraipa
block|{
literal|"840"
block|,
literal|"subdoublevert"
block|}
block|,
comment|// Double vertical line below - requires extraipa
block|{
literal|"841"
block|,
literal|"subcorner"
block|}
block|,
comment|// Left angle below - requires extraipa
block|{
literal|"842"
block|,
literal|"crtilde"
block|}
block|,
comment|// Not tilde above - requires extraipa
block|{
literal|"843"
block|,
literal|"dottedtilde"
block|}
block|,
comment|// Homothetic above - requires extraipa
block|{
literal|"844"
block|,
literal|"doubletilde"
block|}
block|,
comment|// Almost equal to above - requires extraipa
block|{
literal|"845"
block|,
literal|"spreadlips"
block|}
block|,
comment|// Left right arrow below - requires extraipa
block|{
literal|"846"
block|,
literal|"whistle"
block|}
block|,
comment|// Upwards arrow below - requires extraipa
block|{
literal|"861"
block|,
literal|"textdoublebreve"
block|}
block|,
comment|// Double breve
block|{
literal|"862"
block|,
literal|"textdoublemacron"
block|}
block|,
comment|// Double macron
block|{
literal|"863"
block|,
literal|"textdoublemacronbelow"
block|}
block|,
comment|// Double macron below
block|{
literal|"864"
block|,
literal|"textdoubletilde"
block|}
block|,
comment|// Double tilde
block|{
literal|"865"
block|,
literal|"texttoptiebar"
block|}
block|,
comment|// Double inverted breve
block|{
literal|"866"
block|,
literal|"sliding"
block|}
block|,
comment|// Double rightwards arrow below - requires extraipa
block|}
decl_stmt|;
static|static
block|{
for|for
control|(
name|String
index|[]
name|aConversionList
range|:
name|CONVERSION_LIST
control|)
block|{
if|if
condition|(
operator|!
operator|(
name|aConversionList
index|[
literal|2
index|]
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|String
name|strippedLaTeX
init|=
name|cleanLaTeX
argument_list|(
name|aConversionList
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|aConversionList
index|[
literal|1
index|]
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|HTML_LATEX_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"&"
operator|+
name|aConversionList
index|[
literal|1
index|]
operator|+
literal|";"
argument_list|,
name|aConversionList
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|strippedLaTeX
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
name|strippedLaTeX
argument_list|,
literal|"&"
operator|+
name|aConversionList
index|[
literal|1
index|]
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
operator|(
name|aConversionList
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
operator|!
name|strippedLaTeX
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
name|strippedLaTeX
argument_list|,
literal|"&#"
operator|+
name|aConversionList
index|[
literal|0
index|]
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
operator|(
name|aConversionList
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|NUMERICAL_LATEX_CONVERSION_MAP
operator|.
name|put
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|aConversionList
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|aConversionList
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|Integer
operator|.
name|decode
argument_list|(
name|aConversionList
index|[
literal|0
index|]
argument_list|)
operator|>
literal|128
condition|)
block|{
name|String
name|unicodeSymbol
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|Character
operator|.
name|toChars
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|aConversionList
index|[
literal|0
index|]
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|UNICODE_LATEX_CONVERSION_MAP
operator|.
name|put
argument_list|(
name|unicodeSymbol
argument_list|,
name|aConversionList
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|strippedLaTeX
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
name|strippedLaTeX
argument_list|,
name|unicodeSymbol
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
for|for
control|(
name|String
index|[]
name|anAccentList
range|:
name|ACCENT_LIST
control|)
block|{
name|ESCAPED_ACCENTS
operator|.
name|put
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|anAccentList
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|anAccentList
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|UNICODE_ESCAPED_ACCENTS
operator|.
name|put
argument_list|(
name|anAccentList
index|[
literal|1
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Character
operator|.
name|toChars
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|anAccentList
index|[
literal|0
index|]
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Manually added values which are killed by cleanLaTeX
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"$"
argument_list|,
literal|"&dollar;"
argument_list|)
expr_stmt|;
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"$"
argument_list|,
literal|"$"
argument_list|)
expr_stmt|;
comment|// Manual corrections
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"AA"
argument_list|,
literal|"&Aring;"
argument_list|)
expr_stmt|;
comment|// Overwritten by&angst; which is less supported
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"AA"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// Overwritten by Ãngstrom symbol
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"'n"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// Manual additions
comment|// Support relax to the extent that it is simply removed
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"relax"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"relax"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// Support a special version of apostrophe
name|LATEX_HTML_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"textquotesingle"
argument_list|,
literal|"&apos;"
argument_list|)
expr_stmt|;
name|LATEX_UNICODE_CONVERSION_MAP
operator|.
name|put
argument_list|(
literal|"textquotesingle"
argument_list|,
literal|"'"
argument_list|)
expr_stmt|;
comment|// apostrophe, U+00027
block|}
DECL|method|HTMLUnicodeConversionMaps ()
specifier|private
name|HTMLUnicodeConversionMaps
parameter_list|()
block|{     }
DECL|method|cleanLaTeX (String escapedString)
specifier|private
specifier|static
name|String
name|cleanLaTeX
parameter_list|(
name|String
name|escapedString
parameter_list|)
block|{
comment|// Get rid of \{}$ from the LaTeX-string
return|return
name|escapedString
operator|.
name|replaceAll
argument_list|(
literal|"[\\\\\\{\\}\\$]"
argument_list|,
literal|""
argument_list|)
return|;
block|}
block|}
end_class

end_unit

