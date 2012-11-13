begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_class
DECL|class|HTMLConverter
specifier|public
class|class
name|HTMLConverter
implements|implements
name|LayoutFormatter
block|{
comment|/*   Portions Â© International Organization for Standardization 1986:      Permission to copy in any form is granted for use with      conforming SGML systems and applications as defined in      ISO 8879, provided this notice is included in all copies.     */
comment|// most of the LaTeX commands can be read at http://en.wikibooks.org/wiki/LaTeX/Accents
comment|// The symbols can be looked at http://www.fileformat.info/info/unicode/char/a4/index.htm. Replace "a4" with the U+ number
comment|// http://detexify.kirelabs.org/classify.html and http://www.ctan.org/tex-archive/info/symbols/comprehensive/ might help to find the right LaTeX command
comment|// http://llg.cubic.org/docs/ent2latex.html is also useful
DECL|field|conversionList
specifier|private
name|String
index|[]
index|[]
name|conversionList
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
literal|"\\{~\\}"
block|}
block|,
comment|// no-break space = non-breaking space,
comment|//                                 U+00A0 ISOnum
block|{
literal|"161"
block|,
literal|"iexcl"
block|,
literal|"\\\\textexclamdown"
block|}
block|,
comment|// inverted exclamation mark, U+00A1 ISOnum
block|{
literal|"162"
block|,
literal|"cent"
block|,
literal|"\\\\textcent"
block|}
block|,
comment|// cent sign, U+00A2 ISOnum
block|{
literal|"163"
block|,
literal|"pound"
block|,
literal|"\\\\pounds"
block|}
block|,
comment|// pound sign, U+00A3 ISOnum
block|{
literal|"164"
block|,
literal|"curren"
block|,
literal|""
block|}
block|,
comment|// currency sign, U+00A4 ISOnum
block|{
literal|"165"
block|,
literal|"yen"
block|,
literal|"\\\\textyen"
block|}
block|,
comment|// yen sign = yuan sign, U+00A5 ISOnum
block|{
literal|"166"
block|,
literal|"brvbar"
block|,
literal|""
block|}
block|,
comment|// broken bar = broken vertical bar,
comment|//                                 U+00A6 ISOnum
block|{
literal|"167"
block|,
literal|"sect"
block|,
literal|"\\{\\\\S\\}"
block|}
block|,
comment|// section sign, U+00A7 ISOnum
block|{
literal|"168"
block|,
literal|"uml"
block|,
literal|"\\\\\"\\{\\}"
block|}
block|,
comment|// diaeresis = spacing diaeresis,
comment|//                                 U+00A8 ISOdia
block|{
literal|"169"
block|,
literal|"copy"
block|,
literal|"\\\\copyright"
block|}
block|,
comment|// copyright sign, U+00A9 ISOnum
block|{
literal|"170"
block|,
literal|"ordf"
block|,
literal|"\\\\textordfeminine"
block|}
block|,
comment|// feminine ordinal indicator, U+00AA ISOnum
block|{
literal|"171"
block|,
literal|"laquo"
block|,
literal|"\\\\guillemotleft"
block|}
block|,
comment|// left-pointing double angle quotation mark
comment|//                                 = left pointing guillemet, U+00AB ISOnum
block|{
literal|"172"
block|,
literal|"not"
block|,
literal|"\\$\\\\neg\\$"
block|}
block|,
comment|// not sign, U+00AC ISOnum
block|{
literal|"173"
block|,
literal|"shy"
block|,
literal|"\\\\-"
block|}
block|,
comment|// soft hyphen = discretionary hyphen,
comment|//                                 U+00AD ISOnum
block|{
literal|"174"
block|,
literal|"reg"
block|,
literal|"\\\\textregistered"
block|}
block|,
comment|// registered sign = registered trade mark sign,
comment|//                                 U+00AE ISOnum
block|{
literal|"175"
block|,
literal|"macr"
block|,
literal|"\\\\=\\{\\}"
block|}
block|,
comment|// macron = spacing macron = overline
comment|//                                 = APL overbar, U+00AF ISOdia
block|{
literal|"176"
block|,
literal|"deg"
block|,
literal|"\\$\\\\deg\\$"
block|}
block|,
comment|// degree sign, U+00B0 ISOnum
block|{
literal|"177"
block|,
literal|"plusmn"
block|,
literal|"\\$\\\\pm\\$"
block|}
block|,
comment|// plus-minus sign = plus-or-minus sign,
comment|//                                 U+00B1 ISOnum
block|{
literal|"178"
block|,
literal|"sup2"
block|,
literal|"\\$\\^2\\$"
block|}
block|,
comment|// superscript two = superscript digit two
comment|//                                 = squared, U+00B2 ISOnum
block|{
literal|"179"
block|,
literal|"sup3"
block|,
literal|"\\$\\^3\\$"
block|}
block|,
comment|// superscript three = superscript digit three
comment|//                                 = cubed, U+00B3 ISOnum
block|{
literal|"180"
block|,
literal|"acute"
block|,
literal|"\\\\'\\{\\}"
block|}
block|,
comment|// acute accent = spacing acute,
comment|//                                 U+00B4 ISOdia
block|{
literal|"181"
block|,
literal|"micro"
block|,
literal|"\\$\\\\mu\\$"
block|}
block|,
comment|// micro sign, U+00B5 ISOnum
block|{
literal|"182"
block|,
literal|"para"
block|,
literal|"\\{\\\\P\\}"
block|}
block|,
comment|// pilcrow sign = paragraph sign,
comment|//                                 U+00B6 ISOnum
block|{
literal|"183"
block|,
literal|"middot"
block|,
literal|"\\$\\\\cdot\\$"
block|}
block|,
comment|// middle dot = Georgian comma
comment|//                                 = Greek middle dot, U+00B7 ISOnum
block|{
literal|"184"
block|,
literal|"cedil"
block|,
literal|"\\\\c\\{\\}"
block|}
block|,
comment|// cedilla = spacing cedilla, U+00B8 ISOdia
block|{
literal|"185"
block|,
literal|"sup1"
block|,
literal|"\\\\textsuperscript\\{1\\}"
block|}
block|,
comment|// superscript one = superscript digit one,
comment|//                                 U+00B9 ISOnum
block|{
literal|"186"
block|,
literal|"ordm"
block|,
literal|"\\\\textordmasculine"
block|}
block|,
comment|// masculine ordinal indicator,
comment|//                                 U+00BA ISOnum
block|{
literal|"187"
block|,
literal|"raquo"
block|,
literal|"\\\\guillemotright"
block|}
block|,
comment|// right-pointing double angle quotation mark
comment|//                                 = right pointing guillemet, U+00BB ISOnum
block|{
literal|"188"
block|,
literal|"frac14"
block|,
literal|"\\$\\sfrac\\{1\\}\\{4\\}\\$"
block|}
block|,
comment|// vulgar fraction one quarter
comment|//                                 = fraction one quarter, U+00BC ISOnum
block|{
literal|"189"
block|,
literal|"frac12"
block|,
literal|"\\$\\sfrac\\{1\\}\\{2\\}\\$"
block|}
block|,
comment|// vulgar fraction one half
comment|//                                 = fraction one half, U+00BD ISOnum
block|{
literal|"190"
block|,
literal|"frac34"
block|,
literal|"\\$\\sfrac\\{3\\}\\{4\\}\\$"
block|}
block|,
comment|// vulgar fraction three quarters
comment|//                                 = fraction three quarters, U+00BE ISOnum
block|{
literal|"191"
block|,
literal|"iquest"
block|,
literal|"\\\\textquestiondown"
block|}
block|,
comment|// inverted question mark
comment|//                                 = turned question mark, U+00BF ISOnum
block|{
literal|"192"
block|,
literal|"Agrave"
block|,
literal|"\\\\`\\{A\\}"
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
literal|"\\\\'\\{A\\}"
block|}
block|,
comment|// latin capital letter A with acute,
comment|//                                 U+00C1 ISOlat1
block|{
literal|"194"
block|,
literal|"Acirc"
block|,
literal|"\\\\\\^\\{A\\}"
block|}
block|,
comment|// latin capital letter A with circumflex,
comment|//                                 U+00C2 ISOlat1
block|{
literal|"195"
block|,
literal|"Atilde"
block|,
literal|"\\\\~\\{A\\}"
block|}
block|,
comment|// latin capital letter A with tilde,
comment|//                                 U+00C3 ISOlat1
block|{
literal|"196"
block|,
literal|"Auml"
block|,
literal|"\\\"\\{A\\}"
block|}
block|,
comment|// latin capital letter A with diaeresis,
comment|//                                 U+00C4 ISOlat1
block|{
literal|"197"
block|,
literal|"Aring"
block|,
literal|"\\{\\\\AA\\}"
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
literal|"\\{\\\\AE\\}"
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
literal|"\\\\c\\{C\\}"
block|}
block|,
comment|// latin capital letter C with cedilla,
comment|//                                 U+00C7 ISOlat1
block|{
literal|"200"
block|,
literal|"Egrave"
block|,
literal|"\\\\`\\{E\\}"
block|}
block|,
comment|// latin capital letter E with grave,
comment|//                                 U+00C8 ISOlat1
block|{
literal|"201"
block|,
literal|"Eacute"
block|,
literal|"\\\\'\\{E\\}"
block|}
block|,
comment|// latin capital letter E with acute,
comment|//                                 U+00C9 ISOlat1
block|{
literal|"202"
block|,
literal|"Ecirc"
block|,
literal|"\\\\\\^\\{E\\}"
block|}
block|,
comment|// latin capital letter E with circumflex,
comment|//                                 U+00CA ISOlat1
block|{
literal|"203"
block|,
literal|"Euml"
block|,
literal|"\\\\\"\\{E\\}"
block|}
block|,
comment|// latin capital letter E with diaeresis,
comment|//                                 U+00CB ISOlat1
block|{
literal|"204"
block|,
literal|"Igrave"
block|,
literal|"\\\\`\\{I\\}"
block|}
block|,
comment|// latin capital letter I with grave,
comment|//                                 U+00CC ISOlat1
block|{
literal|"205"
block|,
literal|"Iacute"
block|,
literal|"\\\\'\\{I\\}"
block|}
block|,
comment|// latin capital letter I with acute,
comment|//                                 U+00CD ISOlat1
block|{
literal|"206"
block|,
literal|"Icirc"
block|,
literal|"\\\\\\^\\{I\\}"
block|}
block|,
comment|// latin capital letter I with circumflex,
comment|//                                 U+00CE ISOlat1
block|{
literal|"207"
block|,
literal|"Iuml"
block|,
literal|"\\\\\"\\{I\\}"
block|}
block|,
comment|// latin capital letter I with diaeresis,
comment|//                                 U+00CF ISOlat1
block|{
literal|"208"
block|,
literal|"ETH"
block|,
literal|"\\{\\\\DH\\}"
block|}
block|,
comment|// latin capital letter ETH, U+00D0 ISOlat1
block|{
literal|"209"
block|,
literal|"Ntilde"
block|,
literal|"\\\\~\\{N\\}"
block|}
block|,
comment|// latin capital letter N with tilde,
comment|//                                 U+00D1 ISOlat1
block|{
literal|"210"
block|,
literal|"Ograve"
block|,
literal|"\\\\`\\{O\\}"
block|}
block|,
comment|// latin capital letter O with grave,
comment|//                                 U+00D2 ISOlat1
block|{
literal|"211"
block|,
literal|"Oacute"
block|,
literal|"\\\\'\\{O\\}"
block|}
block|,
comment|// latin capital letter O with acute,
comment|//                                 U+00D3 ISOlat1
block|{
literal|"212"
block|,
literal|"Ocirc"
block|,
literal|"\\\\\\^\\{O\\}"
block|}
block|,
comment|// latin capital letter O with circumflex,
comment|//                                 U+00D4 ISOlat1
block|{
literal|"213"
block|,
literal|"Otilde"
block|,
literal|"\\\\~\\{O\\}"
block|}
block|,
comment|// latin capital letter O with tilde,
comment|//                                 U+00D5 ISOlat1
block|{
literal|"214"
block|,
literal|"Ouml"
block|,
literal|"\\\\\"\\{O\\}"
block|}
block|,
comment|// latin capital letter O with diaeresis,
comment|//                                 U+00D6 ISOlat1
block|{
literal|"215"
block|,
literal|"times"
block|,
literal|"\\$\\\\times\\$"
block|}
block|,
comment|// multiplication sign, U+00D7 ISOnum
block|{
literal|"216"
block|,
literal|"Oslash"
block|,
literal|"\\{\\\\O\\{\\}\\}"
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
literal|"\\\\`\\{U\\}"
block|}
block|,
comment|// latin capital letter U with grave,
comment|//                                 U+00D9 ISOlat1
block|{
literal|"218"
block|,
literal|"Uacute"
block|,
literal|"\\\\'\\{U\\}"
block|}
block|,
comment|// latin capital letter U with acute,
comment|//                                 U+00DA ISOlat1
block|{
literal|"219"
block|,
literal|"Ucirc"
block|,
literal|"\\\\\\^\\{U\\}"
block|}
block|,
comment|// latin capital letter U with circumflex,
comment|//                                 U+00DB ISOlat1
block|{
literal|"220"
block|,
literal|"Uuml"
block|,
literal|"\\\\\"\\{U\\}"
block|}
block|,
comment|// latin capital letter U with diaeresis,
comment|//                                 U+00DC ISOlat1
block|{
literal|"221"
block|,
literal|"Yacute"
block|,
literal|"\\\\'\\{Y\\}"
block|}
block|,
comment|// latin capital letter Y with acute,
comment|//                                 U+00DD ISOlat1
block|{
literal|"222"
block|,
literal|"THORN"
block|,
literal|"\\{\\\\TH\\}"
block|}
block|,
comment|// latin capital letter THORN,
comment|//                                 U+00DE ISOlat1
block|{
literal|"223"
block|,
literal|"szlig"
block|,
literal|"\\\\ss\\{\\}"
block|}
block|,
comment|// latin small letter sharp s = ess-zed,
comment|//                                 U+00DF ISOlat1
block|{
literal|"224"
block|,
literal|"agrave"
block|,
literal|"\\\\`\\{a\\}"
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
literal|"\\\\'\\{a\\}"
block|}
block|,
comment|// latin small letter a with acute,
comment|//                                 U+00E1 ISOlat1
block|{
literal|"226"
block|,
literal|"acirc"
block|,
literal|"\\\\\\^\\{a\\}"
block|}
block|,
comment|// latin small letter a with circumflex,
comment|//                                 U+00E2 ISOlat1
block|{
literal|"227"
block|,
literal|"atilde"
block|,
literal|"\\\\~\\{a\\}"
block|}
block|,
comment|// latin small letter a with tilde,
comment|//                                 U+00E3 ISOlat1
block|{
literal|"228"
block|,
literal|"auml"
block|,
literal|"\\\\\"\\{a\\}"
block|}
block|,
comment|// latin small letter a with diaeresis,
comment|//                                 U+00E4 ISOlat1
block|{
literal|"229"
block|,
literal|"aring"
block|,
literal|"\\{\\\\aa\\}"
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
literal|"\\{\\\\ae\\}"
block|}
block|,
comment|// latin small letter ae
comment|//                                 = latin small ligature ae, U+00E6 ISOlat1
block|{
literal|"231"
block|,
literal|"ccedil"
block|,
literal|"\\\\c\\{c\\}"
block|}
block|,
comment|// latin small letter c with cedilla,
comment|//                                 U+00E7 ISOlat1
block|{
literal|"232"
block|,
literal|"egrave"
block|,
literal|"\\\\`\\{e\\}"
block|}
block|,
comment|// latin small letter e with grave,
comment|//                                 U+00E8 ISOlat1
block|{
literal|"233"
block|,
literal|"eacute"
block|,
literal|"\\\\'\\{e\\}"
block|}
block|,
comment|// latin small letter e with acute,
comment|//                                 U+00E9 ISOlat1
block|{
literal|"234"
block|,
literal|"ecirc"
block|,
literal|"\\\\\\^\\{e\\}"
block|}
block|,
comment|// latin small letter e with circumflex,
comment|//                                 U+00EA ISOlat1
block|{
literal|"235"
block|,
literal|"euml"
block|,
literal|"\\\\\"\\{e\\}"
block|}
block|,
comment|// latin small letter e with diaeresis,
comment|//                                 U+00EB ISOlat1
block|{
literal|"236"
block|,
literal|"igrave"
block|,
literal|"\\\\`\\{i\\}"
block|}
block|,
comment|// latin small letter i with grave,
comment|//                                 U+00EC ISOlat1
block|{
literal|"237"
block|,
literal|"iacute"
block|,
literal|"\\\\'\\{i\\}"
block|}
block|,
comment|// latin small letter i with acute,
comment|//                                 U+00ED ISOlat1
block|{
literal|"238"
block|,
literal|"icirc"
block|,
literal|"\\\\\\^\\{i\\}"
block|}
block|,
comment|// latin small letter i with circumflex,
comment|//                                 U+00EE ISOlat1
block|{
literal|"239"
block|,
literal|"iuml"
block|,
literal|"\\\\\"\\{\\\\i\\}"
block|}
block|,
comment|// latin small letter i with diaeresis,
comment|//                                 U+00EF ISOlat1
block|{
literal|"240"
block|,
literal|"eth"
block|,
literal|"\\\\dh"
block|}
block|,
comment|// latin small letter eth, U+00F0 ISOlat1
block|{
literal|"241"
block|,
literal|"ntilde"
block|,
literal|"\\\\~\\{n\\}"
block|}
block|,
comment|// latin small letter n with tilde,
comment|//                                 U+00F1 ISOlat1
block|{
literal|"242"
block|,
literal|"ograve"
block|,
literal|"\\\\`\\{o\\}"
block|}
block|,
comment|// latin small letter o with grave,
comment|//                                 U+00F2 ISOlat1
block|{
literal|"243"
block|,
literal|"oacute"
block|,
literal|"\\\\'\\{o\\}"
block|}
block|,
comment|// latin small letter o with acute,
comment|//                                 U+00F3 ISOlat1
block|{
literal|"244"
block|,
literal|"ocirc"
block|,
literal|"\\\\\\^\\{o\\}"
block|}
block|,
comment|// latin small letter o with circumflex,
comment|//                                 U+00F4 ISOlat1
block|{
literal|"245"
block|,
literal|"otilde"
block|,
literal|"\\\\~\\{o\\}"
block|}
block|,
comment|// latin small letter o with tilde,
comment|//                                 U+00F5 ISOlat1
block|{
literal|"246"
block|,
literal|"ouml"
block|,
literal|"\\\\\"\\{o\\}"
block|}
block|,
comment|// latin small letter o with diaeresis,
comment|//                                 U+00F6 ISOlat1
block|{
literal|"247"
block|,
literal|"divide"
block|,
literal|"/"
block|}
block|,
comment|// division sign, U+00F7 ISOnum
block|{
literal|"248"
block|,
literal|"oslash"
block|,
literal|"\\\\o\\{\\}"
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
literal|"\\\\`\\{u\\}"
block|}
block|,
comment|// latin small letter u with grave,
comment|//                                 U+00F9 ISOlat1
block|{
literal|"250"
block|,
literal|"uacute"
block|,
literal|"\\\\'\\{u\\}"
block|}
block|,
comment|// latin small letter u with acute,
comment|//                                 U+00FA ISOlat1
block|{
literal|"251"
block|,
literal|"ucirc"
block|,
literal|"\\\\\\^\\{u\\}"
block|}
block|,
comment|// latin small letter u with circumflex,
comment|//                                 U+00FB ISOlat1
block|{
literal|"252"
block|,
literal|"uuml"
block|,
literal|"\\\\\"\\{u\\}"
block|}
block|,
comment|// latin small letter u with diaeresis,
comment|//                                 U+00FC ISOlat1
block|{
literal|"253"
block|,
literal|"yacute"
block|,
literal|"\\\\'\\{y\\}"
block|}
block|,
comment|// latin small letter y with acute,
comment|//                                 U+00FD ISOlat1
block|{
literal|"254"
block|,
literal|"thorn"
block|,
literal|"\\{\\\\th\\}"
block|}
block|,
comment|// latin small letter thorn,
comment|//                                 U+00FE ISOlat1
block|{
literal|"255"
block|,
literal|"yuml"
block|,
literal|"\\\\\"\\{y\\}"
block|}
block|,
comment|// latin small letter y with diaeresis,
comment|//                                 U+00FF ISOlat1
block|{
literal|"402"
block|,
literal|"fnof"
block|,
literal|"\\$f\\$"
block|}
block|,
comment|// latin small f with hook = function
comment|//                                   = florin, U+0192 ISOtech
comment|/* Greek */
block|{
literal|"913"
block|,
literal|"Alpha"
block|,
literal|"\\{\\$\\\\Alpha\\$\\}"
block|}
block|,
comment|// greek capital letter alpha, U+0391
block|{
literal|"914"
block|,
literal|"Beta"
block|,
literal|"\\{\\$\\\\Beta\\$\\}"
block|}
block|,
comment|// greek capital letter beta, U+0392
block|{
literal|"915"
block|,
literal|"Gamma"
block|,
literal|"\\{\\$\\\\Gamma\\$\\}"
block|}
block|,
comment|// greek capital letter gamma,
comment|//                                   U+0393 ISOgrk3
block|{
literal|"916"
block|,
literal|"Delta"
block|,
literal|"\\{\\$\\\\Delta\\$\\}"
block|}
block|,
comment|// greek capital letter delta,
comment|//                                   U+0394 ISOgrk3
block|{
literal|"917"
block|,
literal|"Epsilon"
block|,
literal|"\\{\\$\\\\Epsilon\\$\\}"
block|}
block|,
comment|// greek capital letter epsilon, U+0395
block|{
literal|"918"
block|,
literal|"Zeta"
block|,
literal|"\\{\\$\\\\Zeta\\$\\}"
block|}
block|,
comment|// greek capital letter zeta, U+0396
block|{
literal|"919"
block|,
literal|"Eta"
block|,
literal|"\\{\\$\\\\Eta\\$\\}"
block|}
block|,
comment|// greek capital letter eta, U+0397
block|{
literal|"920"
block|,
literal|"Theta"
block|,
literal|"\\{\\$\\\\Theta\\$\\}"
block|}
block|,
comment|// greek capital letter theta,
comment|//                                   U+0398 ISOgrk3
block|{
literal|"921"
block|,
literal|"Iota"
block|,
literal|"\\{\\$\\\\Iota\\$\\}"
block|}
block|,
comment|// greek capital letter iota, U+0399
block|{
literal|"922"
block|,
literal|"Kappa"
block|,
literal|"\\{\\$\\\\Kappa\\$\\}"
block|}
block|,
comment|// greek capital letter kappa, U+039A
block|{
literal|"923"
block|,
literal|"Lambda"
block|,
literal|"\\{\\$\\\\Lambda\\$\\}"
block|}
block|,
comment|// greek capital letter lambda,
comment|//                                   U+039B ISOgrk3
block|{
literal|"924"
block|,
literal|"Mu"
block|,
literal|"\\{\\$\\\\Mu\\$\\}"
block|}
block|,
comment|// greek capital letter mu, U+039C
block|{
literal|"925"
block|,
literal|"Nu"
block|,
literal|"\\{\\$\\\\Nu\\$\\}"
block|}
block|,
comment|// greek capital letter nu, U+039D
block|{
literal|"926"
block|,
literal|"Xi"
block|,
literal|"\\{\\$\\\\Xi\\$\\}"
block|}
block|,
comment|// greek capital letter xi, U+039E ISOgrk3
block|{
literal|"927"
block|,
literal|"Omicron"
block|,
literal|"\\{\\$\\\\Omicron\\$\\}"
block|}
block|,
comment|// greek capital letter omicron, U+039F
block|{
literal|"928"
block|,
literal|"Pi"
block|,
literal|"\\{\\$\\\\Pi\\$\\}"
block|}
block|,
comment|// greek capital letter pi, U+03A0 ISOgrk3
block|{
literal|"929"
block|,
literal|"Rho"
block|,
literal|"\\{\\$\\\\Rho\\$\\}"
block|}
block|,
comment|// greek capital letter rho, U+03A1
comment|/* there is no Sigmaf, and no U+03A2 character either */
block|{
literal|"931"
block|,
literal|"Sigma"
block|,
literal|"\\{\\$\\\\Sigma\\$\\}"
block|}
block|,
comment|// greek capital letter sigma,
comment|//                                   U+03A3 ISOgrk3
block|{
literal|"932"
block|,
literal|"Tau"
block|,
literal|"\\{\\$\\\\Tau\\$\\}"
block|}
block|,
comment|// greek capital letter tau, U+03A4
block|{
literal|"933"
block|,
literal|"Upsilon"
block|,
literal|"\\{\\$\\\\Upsilon\\$\\}"
block|}
block|,
comment|// greek capital letter upsilon,
comment|//                                   U+03A5 ISOgrk3
block|{
literal|"934"
block|,
literal|"Phi"
block|,
literal|"\\{\\$\\\\Phi\\$\\}"
block|}
block|,
comment|// greek capital letter phi,
comment|//                                   U+03A6 ISOgrk3
block|{
literal|"935"
block|,
literal|"Chi"
block|,
literal|"\\{\\$\\\\Chi\\$\\}"
block|}
block|,
comment|// greek capital letter chi, U+03A7
block|{
literal|"936"
block|,
literal|"Psi"
block|,
literal|"\\{\\$\\\\Psi\\$\\}"
block|}
block|,
comment|// greek capital letter psi,
comment|//                                   U+03A8 ISOgrk3
block|{
literal|"937"
block|,
literal|"Omega"
block|,
literal|"\\{\\$\\\\Omega\\$\\}"
block|}
block|,
comment|// greek capital letter omega,
comment|//                                   U+03A9 ISOgrk3
block|{
literal|"945"
block|,
literal|"alpha"
block|,
literal|"\\$\\\\alpha\\$"
block|}
block|,
comment|// greek small letter alpha,
comment|//                                   U+03B1 ISOgrk3
block|{
literal|"946"
block|,
literal|"beta"
block|,
literal|"\\$\\\\beta\\$"
block|}
block|,
comment|// greek small letter beta, U+03B2 ISOgrk3
block|{
literal|"947"
block|,
literal|"gamma"
block|,
literal|"\\$\\\\gamma\\$"
block|}
block|,
comment|// greek small letter gamma,
comment|//                                   U+03B3 ISOgrk3
block|{
literal|"948"
block|,
literal|"delta"
block|,
literal|"\\$\\\\delta\\$"
block|}
block|,
comment|// greek small letter delta,
comment|//                                   U+03B4 ISOgrk3
block|{
literal|"949"
block|,
literal|"epsilon"
block|,
literal|"\\$\\\\epsilon\\$"
block|}
block|,
comment|// greek small letter epsilon,
comment|//                                   U+03B5 ISOgrk3
block|{
literal|"950"
block|,
literal|"zeta"
block|,
literal|"\\$\\\\zeta\\$"
block|}
block|,
comment|// greek small letter zeta, U+03B6 ISOgrk3
block|{
literal|"951"
block|,
literal|"eta"
block|,
literal|"\\$\\\\eta\\$"
block|}
block|,
comment|// greek small letter eta, U+03B7 ISOgrk3
block|{
literal|"952"
block|,
literal|"theta"
block|,
literal|"\\$\\\\theta\\$"
block|}
block|,
comment|// greek small letter theta,
comment|//                                   U+03B8 ISOgrk3
block|{
literal|"953"
block|,
literal|"iota"
block|,
literal|"\\$\\\\iota\\$"
block|}
block|,
comment|// greek small letter iota, U+03B9 ISOgrk3
block|{
literal|"954"
block|,
literal|"kappa"
block|,
literal|"\\$\\\\kappa\\$"
block|}
block|,
comment|// greek small letter kappa,
comment|//                                   U+03BA ISOgrk3
block|{
literal|"955"
block|,
literal|"lambda"
block|,
literal|"\\$\\\\lambda\\$"
block|}
block|,
comment|// greek small letter lambda,
comment|//                                   U+03BB ISOgrk3
block|{
literal|"956"
block|,
literal|"mu"
block|,
literal|"\\$\\\\mu\\$"
block|}
block|,
comment|// greek small letter mu, U+03BC ISOgrk3
block|{
literal|"957"
block|,
literal|"nu"
block|,
literal|"\\$\\\\nu\\$"
block|}
block|,
comment|// greek small letter nu, U+03BD ISOgrk3
block|{
literal|"958"
block|,
literal|"xi"
block|,
literal|"\\$\\\\xi\\$"
block|}
block|,
comment|// greek small letter xi, U+03BE ISOgrk3
block|{
literal|"959"
block|,
literal|"omicron"
block|,
literal|"\\$\\\\omicron\\$"
block|}
block|,
comment|// greek small letter omicron, U+03BF NEW
block|{
literal|"960"
block|,
literal|"pi"
block|,
literal|"\\$\\\\phi\\$"
block|}
block|,
comment|// greek small letter pi, U+03C0 ISOgrk3
block|{
literal|"961"
block|,
literal|"rho"
block|,
literal|"\\$\\\\rho\\$"
block|}
block|,
comment|// greek small letter rho, U+03C1 ISOgrk3
block|{
literal|"962"
block|,
literal|"sigmaf"
block|,
literal|"\\$\\\\varsigma\\$"
block|}
block|,
comment|// greek small letter final sigma,
comment|//                                   U+03C2 ISOgrk3
block|{
literal|"963"
block|,
literal|"sigma"
block|,
literal|"\\$\\\\sigma\\$"
block|}
block|,
comment|// greek small letter sigma,
comment|//                                   U+03C3 ISOgrk3
block|{
literal|"964"
block|,
literal|"tau"
block|,
literal|"\\$\\\\tau\\$"
block|}
block|,
comment|// greek small letter tau, U+03C4 ISOgrk3
block|{
literal|"965"
block|,
literal|"upsilon"
block|,
literal|"\\$\\\\upsilon\\$"
block|}
block|,
comment|// greek small letter upsilon,
comment|//                                   U+03C5 ISOgrk3
block|{
literal|"966"
block|,
literal|"phi"
block|,
literal|"\\$\\\\phi\\$"
block|}
block|,
comment|// greek small letter phi, U+03C6 ISOgrk3
block|{
literal|"967"
block|,
literal|"chi"
block|,
literal|"\\$\\\\chi\\$"
block|}
block|,
comment|// greek small letter chi, U+03C7 ISOgrk3
block|{
literal|"968"
block|,
literal|"psi"
block|,
literal|"\\$\\\\psi\\$"
block|}
block|,
comment|// greek small letter psi, U+03C8 ISOgrk3
block|{
literal|"969"
block|,
literal|"omega"
block|,
literal|"\\$\\\\omega\\$"
block|}
block|,
comment|// greek small letter omega,
comment|//                                   U+03C9 ISOgrk3
block|{
literal|"977"
block|,
literal|"thetasym"
block|,
literal|"\\$\\\\vartheta\\$"
block|}
block|,
comment|// greek small letter theta symbol,
comment|//                                   U+03D1 NEW
block|{
literal|"978"
block|,
literal|"upsih"
block|,
literal|""
block|}
block|,
comment|// greek upsilon with hook symbol,
comment|//                                   U+03D2 NEW
block|{
literal|"982"
block|,
literal|"piv"
block|,
literal|"\\$\\\\varphi\\$"
block|}
block|,
comment|// greek pi symbol, U+03D6 ISOgrk3
comment|/* General Punctuation */
block|{
literal|"8226"
block|,
literal|"bull"
block|,
literal|"\\$\\\\bullet\\$"
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
literal|"\\{\\\\ldots\\}"
block|}
block|,
comment|// horizontal ellipsis = three dot leader,
comment|//                                    U+2026 ISOpub
block|{
literal|"8242"
block|,
literal|"prime"
block|,
literal|"\\$\\\\prime\\$"
block|}
block|,
comment|// prime = minutes = feet, U+2032 ISOtech
block|{
literal|"8243"
block|,
literal|"Prime"
block|,
literal|"\\$\\{''\\}\\$"
block|}
block|,
comment|// double prime = seconds = inches,
comment|//                                    U+2033 ISOtech
block|{
literal|"8254"
block|,
literal|"oline"
block|,
literal|"\\\\=\\{\\}"
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
literal|"\\$\\\\wp\\$"
block|}
block|,
comment|// script capital P = power set
comment|//                                    = Weierstrass p, U+2118 ISOamso
block|{
literal|"8465"
block|,
literal|"image"
block|,
literal|"\\{\\$\\\\Im\\$\\}"
block|}
block|,
comment|// blackletter capital I = imaginary part,
comment|//                                    U+2111 ISOamso
block|{
literal|"8476"
block|,
literal|"real"
block|,
literal|"\\{\\$\\\\Re\\$\\}"
block|}
block|,
comment|// blackletter capital R = real part symbol,
comment|//                                    U+211C ISOamso
block|{
literal|"8482"
block|,
literal|"trade"
block|,
literal|"\\\\texttrademark"
block|}
block|,
comment|// trade mark sign, U+2122 ISOnum
block|{
literal|"8501"
block|,
literal|"alefsym"
block|,
literal|"\\$\\\\aleph\\$"
block|}
block|,
comment|// alef symbol = first transfinite cardinal,
comment|//                                    U+2135 NEW
comment|/*    alef symbol is NOT the same as hebrew letter alef,          U+05D0 although the same glyph could be used to depict both characters */
comment|/* Arrows */
block|{
literal|"8592"
block|,
literal|"larr"
block|,
literal|"\\$\\\\leftarrow\\$"
block|}
block|,
comment|// leftwards arrow, U+2190 ISOnum
block|{
literal|"8593"
block|,
literal|"uarr"
block|,
literal|"\\$\\\\uparrow\\$"
block|}
block|,
comment|// upwards arrow, U+2191 ISOnum
block|{
literal|"8594"
block|,
literal|"rarr"
block|,
literal|"\\$\\\\rightarrow\\$"
block|}
block|,
comment|// rightwards arrow, U+2192 ISOnum
block|{
literal|"8595"
block|,
literal|"darr"
block|,
literal|"\\$\\\\downarrow\\$"
block|}
block|,
comment|// downwards arrow, U+2193 ISOnum
block|{
literal|"8596"
block|,
literal|"harr"
block|,
literal|"\\$\\\\leftrightarrow\\$"
block|}
block|,
comment|// left right arrow, U+2194 ISOamsa
block|{
literal|"8629"
block|,
literal|"crarr"
block|,
literal|""
block|}
block|,
comment|// downwards arrow with corner leftwards
comment|//                                    = carriage return, U+21B5 NEW
block|{
literal|"8656"
block|,
literal|"lArr"
block|,
literal|"\\{\\$\\\\Leftarrow\\$\\}"
block|}
block|,
comment|// leftwards double arrow, U+21D0 ISOtech
comment|/*  ISO 10646 does not say that lArr is the same as the 'is implied by' arrow          but also does not have any other character for that function. So ? lArr can          be used for 'is implied by' as ISOtech suggests */
block|{
literal|"8657"
block|,
literal|"uArr"
block|,
literal|"\\{\\$\\\\Uparrow\\$\\}"
block|}
block|,
comment|// upwards double arrow, U+21D1 ISOamsa
block|{
literal|"8658"
block|,
literal|"rArr"
block|,
literal|"\\{\\$\\\\Rightarrow\\$\\}"
block|}
block|,
comment|// rightwards double arrow,
comment|//                                     U+21D2 ISOtech
comment|/*   ISO 10646 does not say this is the 'implies' character but does not have           another character with this function so ?          rArr can be used for 'implies' as ISOtech suggests */
block|{
literal|"8659"
block|,
literal|"dArr"
block|,
literal|"\\{\\$\\\\Downarrow\\$\\}"
block|}
block|,
comment|// downwards double arrow, U+21D3 ISOamsa
block|{
literal|"8660"
block|,
literal|"hArr"
block|,
literal|"\\{\\$\\\\Leftrightarrow\\$\\}"
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
literal|"\\$\\\\forall\\$"
block|}
block|,
comment|// for all, U+2200 ISOtech
block|{
literal|"8706"
block|,
literal|"part"
block|,
literal|"\\$\\\\partial\\$"
block|}
block|,
comment|// partial differential, U+2202 ISOtech
block|{
literal|"8707"
block|,
literal|"exist"
block|,
literal|"\\$\\\\exists\\$"
block|}
block|,
comment|// there exists, U+2203 ISOtech
block|{
literal|"8709"
block|,
literal|"empty"
block|,
literal|"\\$\\\\emptyset\\$"
block|}
block|,
comment|// empty set = null set = diameter,
comment|//                                    U+2205 ISOamso
block|{
literal|"8711"
block|,
literal|"nabla"
block|,
literal|"\\$\\\\nabla\\$"
block|}
block|,
comment|// nabla = backward difference,
comment|//                                    U+2207 ISOtech
block|{
literal|"8712"
block|,
literal|"isin"
block|,
literal|"\\$\\\\in\\$"
block|}
block|,
comment|// element of, U+2208 ISOtech
block|{
literal|"8713"
block|,
literal|"notin"
block|,
literal|"\\$\\\\notin\\$"
block|}
block|,
comment|// not an element of, U+2209 ISOtech
block|{
literal|"8715"
block|,
literal|"ni"
block|,
literal|"\\$\\\\ni\\$"
block|}
block|,
comment|// contains as member, U+220B ISOtech
comment|/* should there be a more memorable name than 'ni'? */
block|{
literal|"8719"
block|,
literal|"prod"
block|,
literal|"\\$\\\\prod\\$"
block|}
block|,
comment|// n-ary product = product sign,
comment|//                                    U+220F ISOamsb
comment|/*    prod is NOT the same character as U+03A0 'greek capital letter pi' though          the same glyph might be used for both  */
block|{
literal|"8721"
block|,
literal|"sum"
block|,
literal|"\\$\\\\sum\\$"
block|}
block|,
comment|// n-ary sumation, U+2211 ISOamsb
comment|/*    sum is NOT the same character as U+03A3 'greek capital letter sigma'          though the same glyph might be used for both */
block|{
literal|"8722"
block|,
literal|"minus"
block|,
literal|"\\$-\\$"
block|}
block|,
comment|// minus sign, U+2212 ISOtech
block|{
literal|"8727"
block|,
literal|"lowast"
block|,
literal|"\\$\\\\ast\\$"
block|}
block|,
comment|// asterisk operator, U+2217 ISOtech
block|{
literal|"8730"
block|,
literal|"radic"
block|,
literal|"\\$\\\\sqrt{}\\$"
block|}
block|,
comment|// square root = radical sign,
comment|//                                    U+221A ISOtech
block|{
literal|"8733"
block|,
literal|"prop"
block|,
literal|"\\$\\\\propto\\$"
block|}
block|,
comment|// proportional to, U+221D ISOtech
block|{
literal|"8734"
block|,
literal|"infin"
block|,
literal|"\\$\\\\infty\\$"
block|}
block|,
comment|// infinity, U+221E ISOtech
block|{
literal|"8736"
block|,
literal|"ang"
block|,
literal|"\\$\\\\angle\\$"
block|}
block|,
comment|// angle, U+2220 ISOamso
block|{
literal|"8743"
block|,
literal|"and"
block|,
literal|"\\$\\\\land\\$"
block|}
block|,
comment|// logical and = wedge, U+2227 ISOtech
block|{
literal|"8744"
block|,
literal|"or"
block|,
literal|"\\$\\\\lor\\$"
block|}
block|,
comment|// logical or = vee, U+2228 ISOtech
block|{
literal|"8745"
block|,
literal|"cap"
block|,
literal|"\\$\\\\cap\\$"
block|}
block|,
comment|// intersection = cap, U+2229 ISOtech
block|{
literal|"8746"
block|,
literal|"cup"
block|,
literal|"\\$\\\\cup\\$"
block|}
block|,
comment|// union = cup, U+222A ISOtech
block|{
literal|"8747"
block|,
literal|"int"
block|,
literal|"\\$\\\\int\\$"
block|}
block|,
comment|// integral, U+222B ISOtech
block|{
literal|"8756"
block|,
literal|"there4"
block|,
literal|"\\$\\\\uptherefore\\$"
block|}
block|,
comment|// therefore, U+2234 ISOtech; only in LaTeX package MnSymbol
block|{
literal|"8764"
block|,
literal|"sim"
block|,
literal|"\\$\\\\sim\\$"
block|}
block|,
comment|// tilde operator = varies with = similar to,
comment|//                                    U+223C ISOtech
comment|/*  tilde operator is NOT the same character as the tilde, U+007E,          although the same glyph might be used to represent both   */
block|{
literal|"8773"
block|,
literal|"cong"
block|,
literal|"\\$\\\\cong\\$"
block|}
block|,
comment|// approximately equal to, U+2245 ISOtech
block|{
literal|"8776"
block|,
literal|"asymp"
block|,
literal|"\\$\\\\approx\\$"
block|}
block|,
comment|// almost equal to = asymptotic to,
comment|//                                    U+2248 ISOamsr
block|{
literal|"8800"
block|,
literal|"ne"
block|,
literal|"\\$\\\\neq\\$"
block|}
block|,
comment|// not equal to, U+2260 ISOtech
block|{
literal|"8801"
block|,
literal|"equiv"
block|,
literal|"\\$\\\\equiv\\$"
block|}
block|,
comment|// identical to, U+2261 ISOtech
block|{
literal|"8804"
block|,
literal|"le"
block|,
literal|"\\$\\\\leq\\$"
block|}
block|,
comment|// less-than or equal to, U+2264 ISOtech
block|{
literal|"8805"
block|,
literal|"ge"
block|,
literal|"\\$\\\\geq\\$"
block|}
block|,
comment|// greater-than or equal to,
comment|//                                    U+2265 ISOtech
block|{
literal|"8834"
block|,
literal|"sub"
block|,
literal|"\\$\\\\subset\\$"
block|}
block|,
comment|// subset of, U+2282 ISOtech
block|{
literal|"8835"
block|,
literal|"sup"
block|,
literal|"\\$\\\\supset\\$"
block|}
block|,
comment|// superset of, U+2283 ISOtech
comment|/*    note that nsup, 'not a superset of, U+2283' is not covered by the Symbol           font encoding and is not included. Should it be, for symmetry?          It is in ISOamsn   */
block|{
literal|"8836"
block|,
literal|"nsub"
block|,
literal|""
block|}
block|,
comment|// not a subset of, U+2284 ISOamsn
block|{
literal|"8838"
block|,
literal|"sube"
block|,
literal|"\\$\\\\subseteq\\$"
block|}
block|,
comment|// subset of or equal to, U+2286 ISOtech
block|{
literal|"8839"
block|,
literal|"supe"
block|,
literal|"\\$\\\\supseteq\\$"
block|}
block|,
comment|// superset of or equal to,
comment|//                                    U+2287 ISOtech
block|{
literal|"8853"
block|,
literal|"oplus"
block|,
literal|"\\$\\\\oplus\\$"
block|}
block|,
comment|// circled plus = direct sum,
comment|//                                    U+2295 ISOamsb
block|{
literal|"8855"
block|,
literal|"otimes"
block|,
literal|"\\$\\\\otimes\\$"
block|}
block|,
comment|// circled times = vector product,
comment|//                                    U+2297 ISOamsb
block|{
literal|"8869"
block|,
literal|"perp"
block|,
literal|"\\$\\\\perp\\$"
block|}
block|,
comment|// up tack = orthogonal to = perpendicular,
comment|//                                    U+22A5 ISOtech
block|{
literal|"8901"
block|,
literal|"sdot"
block|,
literal|"\\$\\\\cdot\\$"
block|}
block|,
comment|// dot operator, U+22C5 ISOamsb
comment|/* dot operator is NOT the same character as U+00B7 middle dot */
comment|/* Miscellaneous Technical */
block|{
literal|"8968"
block|,
literal|"lceil"
block|,
literal|"\\$\\\\lceil\\$"
block|}
block|,
comment|// left ceiling = apl upstile,
comment|//                                    U+2308 ISOamsc
block|{
literal|"8969"
block|,
literal|"rceil"
block|,
literal|"\\$\\\\rceil\\$"
block|}
block|,
comment|// right ceiling, U+2309 ISOamsc
block|{
literal|"8970"
block|,
literal|"lfloor"
block|,
literal|"\\$\\\\lfloor\\$"
block|}
block|,
comment|// left floor = apl downstile,
comment|//                                    U+230A ISOamsc
block|{
literal|"8971"
block|,
literal|"rfloor"
block|,
literal|"\\$\\\\rfloor\\$"
block|}
block|,
comment|// right floor, U+230B ISOamsc
block|{
literal|"9001"
block|,
literal|"lang"
block|,
literal|"\\$\\\\langle\\$"
block|}
block|,
comment|// left-pointing angle bracket = bra,
comment|//                                    U+2329 ISOtech
comment|/*    lang is NOT the same character as U+003C 'less than'           or U+2039 'single left-pointing angle quotation mark' */
block|{
literal|"9002"
block|,
literal|"rang"
block|,
literal|"\\$\\\\rangle\\$"
block|}
block|,
comment|// right-pointing angle bracket = ket,
comment|//                                    U+232A ISOtech
comment|/*    rang is NOT the same character as U+003E 'greater than'           or U+203A 'single right-pointing angle quotation mark' */
comment|/* Geometric Shapes */
block|{
literal|"9674"
block|,
literal|"loz"
block|,
literal|"\\$\\\\lozenge\\$"
block|}
block|,
comment|// lozenge, U+25CA ISOpub
comment|/* Miscellaneous Symbols */
block|{
literal|"9824"
block|,
literal|"spades"
block|,
literal|""
block|}
block|,
comment|// black spade suit, U+2660 ISOpub
comment|/* black here seems to mean filled as opposed to hollow */
block|{
literal|"9827"
block|,
literal|"clubs"
block|,
literal|""
block|}
block|,
comment|// black club suit = shamrock,
comment|//                                    U+2663 ISOpub
block|{
literal|"9829"
block|,
literal|"hearts"
block|,
literal|""
block|}
block|,
comment|// black heart suit = valentine,
comment|//                                    U+2665 ISOpub
block|{
literal|"9830"
block|,
literal|"diams"
block|,
literal|""
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
literal|"\\\\&"
block|}
block|,
comment|// ampersand, U+0026 ISOnum
block|{
literal|"60"
block|,
literal|"lt"
block|,
literal|"\\$<\\$"
block|}
block|,
comment|// less-than sign, U+003C ISOnum
block|{
literal|"62"
block|,
literal|"gt"
block|,
literal|"\\$>\\$"
block|}
block|,
comment|// greater-than sign, U+003E ISOnum
comment|/* Latin Extended-A */
block|{
literal|"338"
block|,
literal|"OElig"
block|,
literal|"\\{\\\\OE\\}"
block|}
block|,
comment|// latin capital ligature OE,
comment|//                                   U+0152 ISOlat2
block|{
literal|"339"
block|,
literal|"oelig"
block|,
literal|"\\{\\\\oe\\}"
block|}
block|,
comment|// latin small ligature oe, U+0153 ISOlat2
comment|/* ligature is a misnomer, this is a separate character in some languages */
block|{
literal|"352"
block|,
literal|"Scaron"
block|,
literal|""
block|}
block|,
comment|// latin capital letter S with caron,
comment|//                                   U+0160 ISOlat2
block|{
literal|"353"
block|,
literal|"scaron"
block|,
literal|""
block|}
block|,
comment|// latin small letter s with caron,
comment|//                                   U+0161 ISOlat2
block|{
literal|"376"
block|,
literal|"Yuml"
block|,
literal|"\\\\\"\\{Y\\}"
block|}
block|,
comment|// latin capital letter Y with diaeresis,
comment|//                                   U+0178 ISOlat2
comment|/* Spacing Modifier Letters */
block|{
literal|"710"
block|,
literal|"circ"
block|,
literal|"\\\\textasciicircum"
block|}
block|,
comment|// modifier letter circumflex accent,
comment|//                                   U+02C6 ISOpub
block|{
literal|"732"
block|,
literal|"tilde"
block|,
literal|"\\\\textasciitilde"
block|}
block|,
comment|// small tilde, U+02DC ISOdia
comment|/* General Punctuation */
block|{
literal|"8194"
block|,
literal|"ensp"
block|,
literal|"\\\\hspace\\{0.5em\\}"
block|}
block|,
comment|// en space, U+2002 ISOpub
block|{
literal|"8195"
block|,
literal|"emsp"
block|,
literal|"\\\\hspace\\{1em\\}"
block|}
block|,
comment|// em space, U+2003 ISOpub
block|{
literal|"8201"
block|,
literal|"thinsp"
block|,
literal|"\\\\hspace\\{0.167em\\}"
block|}
block|,
comment|// thin space, U+2009 ISOpub
block|{
literal|"8204"
block|,
literal|"zwnj"
block|,
literal|""
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
literal|"`"
block|}
block|,
comment|// left single quotation mark,
comment|//                                   U+2018 ISOnum
block|{
literal|"8217"
block|,
literal|"rsquo"
block|,
literal|"'"
block|}
block|,
comment|// right single quotation mark,
comment|//                                   U+2019 ISOnum
block|{
literal|"8218"
block|,
literal|"sbquo"
block|,
literal|""
block|}
block|,
comment|// single low-9 quotation mark, U+201A NEW
block|{
literal|"8220"
block|,
literal|"ldquo"
block|,
literal|"``"
block|}
block|,
comment|// left double quotation mark,
comment|//                                   U+201C ISOnum
block|{
literal|"8221"
block|,
literal|"rdquo"
block|,
literal|"''"
block|}
block|,
comment|// right double quotation mark,
comment|//                                   U+201D ISOnum
block|{
literal|"8222"
block|,
literal|"bdquo"
block|,
literal|""
block|}
block|,
comment|// double low-9 quotation mark, U+201E NEW
block|{
literal|"8224"
block|,
literal|"dagger"
block|,
literal|"\\\\dag"
block|}
block|,
comment|// dagger, U+2020 ISOpub
block|{
literal|"8225"
block|,
literal|"Dagger"
block|,
literal|"\\\\ddag"
block|}
block|,
comment|// double dagger, U+2021 ISOpub
block|{
literal|"8240"
block|,
literal|"permil"
block|,
literal|"\\\\textperthousand"
block|}
block|,
comment|// per mille sign, U+2030 ISOtech
block|{
literal|"8249"
block|,
literal|"lsaquo"
block|,
literal|""
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
literal|""
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
literal|""
block|}
block|,
comment|// euro sign, U+20AC NEW
comment|/* Manually added */
block|{
literal|"37"
block|,
literal|"percnt"
block|,
literal|"\\\\%"
block|}
block|,
comment|// Percent
block|{
literal|"43"
block|,
literal|""
block|,
literal|"\\+"
block|}
block|,
comment|// Plus
block|{
literal|"95"
block|,
literal|""
block|,
literal|"\\\\_"
block|}
block|,
comment|// Underscore
block|{
literal|"123"
block|,
literal|""
block|,
literal|"\\\\\\{"
block|}
block|,
comment|// Left curly bracket
block|{
literal|"125"
block|,
literal|""
block|,
literal|"\\\\\\}"
block|}
block|,
comment|// Right curly bracket
block|{
literal|"146"
block|,
literal|""
block|,
literal|"'"
block|}
block|,
comment|// Private use two ???
block|{
literal|"305"
block|,
literal|"inodot"
block|,
literal|"\\{\\\\i\\}"
block|}
block|,
comment|// Small i without the dot
block|{
literal|"769"
block|,
literal|""
block|,
literal|"'"
block|}
block|,
comment|// Can be solved better as it is a combining accent
block|{
literal|"774"
block|,
literal|""
block|,
literal|"\\\\u\\{\\}"
block|}
block|,
comment|// FIX: Breve - Can be solved better as it is a combining accent
block|{
literal|"776"
block|,
literal|""
block|,
literal|"\\\\\"\\{\\}"
block|}
block|,
comment|// FIX: Diaeresis - Can be solved better as it is a combining accent
block|{
literal|"780"
block|,
literal|""
block|,
literal|"\\\\v\\{\\}"
block|}
block|,
comment|// FIX: Caron - Can be solved better as it is a combining accent
block|{
literal|"2013"
block|,
literal|""
block|,
literal|""
block|}
block|,
comment|// NKO letter FA
block|{
literal|"8208"
block|,
literal|""
block|,
literal|"-"
block|}
block|,
comment|// Hyphen
block|{
literal|"8729"
block|,
literal|""
block|,
literal|"\\$\\\\bullet\\$"
block|}
block|,
comment|// Bullet operator
block|{
literal|"10877"
block|,
literal|"les"
block|,
literal|"\\$\\\\leqslant\\$"
block|}
comment|// Less than slanted equal -- requires amssymb
block|}
decl_stmt|;
DECL|field|escapedSymbols
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|escapedSymbols
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|numSymbols
specifier|private
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|numSymbols
init|=
operator|new
name|HashMap
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|HTMLConverter ()
specifier|public
name|HTMLConverter
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|conversionList
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|conversionList
index|[
name|i
index|]
index|[
literal|2
index|]
operator|.
name|length
argument_list|()
operator|>=
literal|1
condition|)
block|{
if|if
condition|(
name|conversionList
index|[
name|i
index|]
index|[
literal|1
index|]
operator|.
name|length
argument_list|()
operator|>=
literal|1
condition|)
block|{
name|escapedSymbols
operator|.
name|put
argument_list|(
literal|"&"
operator|+
name|conversionList
index|[
name|i
index|]
index|[
literal|1
index|]
operator|+
literal|";"
argument_list|,
name|conversionList
index|[
name|i
index|]
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|conversionList
index|[
name|i
index|]
index|[
literal|0
index|]
operator|.
name|length
argument_list|()
operator|>=
literal|1
condition|)
block|{
name|numSymbols
operator|.
name|put
argument_list|(
name|Integer
operator|.
name|decode
argument_list|(
name|conversionList
index|[
name|i
index|]
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|conversionList
index|[
name|i
index|]
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// Deal with the form<sup>k</sup>
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"<sup>([^<]+)</sup>"
argument_list|,
literal|"\\$\\^$1\\$"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|c
init|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'<'
condition|)
block|{
name|i
operator|=
name|readTag
argument_list|(
name|text
argument_list|,
name|sb
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|text
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|patterns
init|=
name|escapedSymbols
operator|.
name|keySet
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|pattern
range|:
name|patterns
control|)
block|{
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
name|pattern
argument_list|,
name|escapedSymbols
operator|.
name|get
argument_list|(
name|pattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Pattern
name|escapedPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&#([x]*)([0]*)(\\p{XDigit}+);"
argument_list|)
decl_stmt|;
name|Matcher
name|m
init|=
name|escapedPattern
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
comment|//	    System.err.println("Found pattern: " + m.group(1));
comment|//      System.err.println("Found pattern: " + m.group(2));
name|int
name|num
init|=
name|Integer
operator|.
name|decode
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"x"
argument_list|,
literal|"#"
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|numSymbols
operator|.
name|containsKey
argument_list|(
name|num
argument_list|)
condition|)
block|{
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"&#"
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
literal|";"
argument_list|,
name|numSymbols
operator|.
name|get
argument_list|(
name|num
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"HTML escaped char not converted "
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
operator|+
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
operator|+
literal|": "
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|num
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Find non-covered special characters with alphabetic codes
name|escapedPattern
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"&(\\w+);"
argument_list|)
expr_stmt|;
name|m
operator|=
name|escapedPattern
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
expr_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"HTML escaped char not converted "
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|text
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|field|MAX_TAG_LENGTH
specifier|private
specifier|final
name|int
name|MAX_TAG_LENGTH
init|=
literal|30
decl_stmt|;
comment|/*private final int MAX_CHAR_LENGTH = 10;      private int readHtmlChar(String text, StringBuffer sb, int position) {         // Have just read the< character that starts the tag.         int index = text.indexOf(';', position);         if ((index> position)&& (index-position< MAX_CHAR_LENGTH)) {         	//String code = text.substring(position, index);             //System.out.println("Removed code: "+text.substring(position, index));             return index; // Just skip the tag.         } else return position; // Don't do anything.     }*/
DECL|method|readTag (String text, StringBuffer sb, int position)
specifier|private
name|int
name|readTag
parameter_list|(
name|String
name|text
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|position
parameter_list|)
block|{
comment|// Have just read the< character that starts the tag.
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'>'
argument_list|,
name|position
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>
name|position
operator|)
operator|&&
operator|(
name|index
operator|-
name|position
operator|<
name|MAX_TAG_LENGTH
operator|)
condition|)
block|{
comment|//System.out.println("Removed tag: "+text.substring(position, index));
return|return
name|index
return|;
comment|// Just skip the tag.
block|}
else|else
return|return
name|position
return|;
comment|// Don't do anything.
block|}
block|}
end_class

end_unit

