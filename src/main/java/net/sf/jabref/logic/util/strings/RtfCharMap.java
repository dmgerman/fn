begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
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

begin_class
DECL|class|RtfCharMap
specifier|public
class|class
name|RtfCharMap
block|{
DECL|field|rtfMap
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|rtfMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|RtfCharMap ()
specifier|public
name|RtfCharMap
parameter_list|()
block|{
name|put
argument_list|(
literal|"`a"
argument_list|,
literal|"\\'e0"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`e"
argument_list|,
literal|"\\'e8"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`i"
argument_list|,
literal|"\\'ec"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`o"
argument_list|,
literal|"\\'f2"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`u"
argument_list|,
literal|"\\'f9"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'a"
argument_list|,
literal|"\\'e1"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'e"
argument_list|,
literal|"\\'e9"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'i"
argument_list|,
literal|"\\'ed"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'o"
argument_list|,
literal|"\\'f3"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'u"
argument_list|,
literal|"\\'fa"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^a"
argument_list|,
literal|"\\'e2"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^e"
argument_list|,
literal|"\\'ea"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^i"
argument_list|,
literal|"\\'ee"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^o"
argument_list|,
literal|"\\'f4"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^u"
argument_list|,
literal|"\\'fa"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"a"
argument_list|,
literal|"\\'e4"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"e"
argument_list|,
literal|"\\'eb"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"i"
argument_list|,
literal|"\\'ef"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"o"
argument_list|,
literal|"\\'f6"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"u"
argument_list|,
literal|"\\u252u"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"~n"
argument_list|,
literal|"\\'f1"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`A"
argument_list|,
literal|"\\'c0"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`E"
argument_list|,
literal|"\\'c8"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`I"
argument_list|,
literal|"\\'cc"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`O"
argument_list|,
literal|"\\'d2"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"`U"
argument_list|,
literal|"\\'d9"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'A"
argument_list|,
literal|"\\'c1"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'E"
argument_list|,
literal|"\\'c9"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'I"
argument_list|,
literal|"\\'cd"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'O"
argument_list|,
literal|"\\'d3"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'U"
argument_list|,
literal|"\\'da"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^A"
argument_list|,
literal|"\\'c2"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^E"
argument_list|,
literal|"\\'ca"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^I"
argument_list|,
literal|"\\'ce"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^O"
argument_list|,
literal|"\\'d4"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"^U"
argument_list|,
literal|"\\'db"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"A"
argument_list|,
literal|"\\'c4"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"E"
argument_list|,
literal|"\\'cb"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"I"
argument_list|,
literal|"\\'cf"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"O"
argument_list|,
literal|"\\'d6"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"\"U"
argument_list|,
literal|"\\'dc"
argument_list|)
expr_stmt|;
comment|// Use UNICODE characters for RTF-Chars which can not be found in the
comment|// standard codepage
name|put
argument_list|(
literal|"S"
argument_list|,
literal|"\\u167S"
argument_list|)
expr_stmt|;
comment|// Section sign
name|put
argument_list|(
literal|"`!"
argument_list|,
literal|"\\u161!"
argument_list|)
expr_stmt|;
comment|// Inverted exclamation point
name|put
argument_list|(
literal|"pounds"
argument_list|,
literal|"\\u163?"
argument_list|)
expr_stmt|;
comment|// Pound sign
name|put
argument_list|(
literal|"copyright"
argument_list|,
literal|"\\u169?"
argument_list|)
expr_stmt|;
comment|// Copyright sign
name|put
argument_list|(
literal|"P"
argument_list|,
literal|"\\u182P"
argument_list|)
expr_stmt|;
comment|// Paragraph sign
name|put
argument_list|(
literal|"`?"
argument_list|,
literal|"\\u191?"
argument_list|)
expr_stmt|;
comment|// Inverted question mark
name|put
argument_list|(
literal|"~A"
argument_list|,
literal|"\\u195A"
argument_list|)
expr_stmt|;
comment|// "Atilde"
name|put
argument_list|(
literal|"AA"
argument_list|,
literal|"\\u197A"
argument_list|)
expr_stmt|;
comment|// "Aring"
comment|// RTFCHARS.put("AE", "{\\uc2\\u198AE}"); // "AElig"
name|put
argument_list|(
literal|"AE"
argument_list|,
literal|"{\\u198AE}"
argument_list|)
expr_stmt|;
comment|// "AElig"
name|put
argument_list|(
literal|"cC"
argument_list|,
literal|"\\u199C"
argument_list|)
expr_stmt|;
comment|// "Ccedil"
name|put
argument_list|(
literal|"DH"
argument_list|,
literal|"\\u208D"
argument_list|)
expr_stmt|;
comment|// "ETH"
name|put
argument_list|(
literal|"~N"
argument_list|,
literal|"\\u209N"
argument_list|)
expr_stmt|;
comment|// "Ntilde"
name|put
argument_list|(
literal|"~O"
argument_list|,
literal|"\\u213O"
argument_list|)
expr_stmt|;
comment|// "Otilde"
comment|// According to ISO 8859-1 the "\times" symbol should be placed here
comment|// (#215).
comment|// Omitting this, because it is a mathematical symbol.
name|put
argument_list|(
literal|"O"
argument_list|,
literal|"\\u216O"
argument_list|)
expr_stmt|;
comment|// "Oslash"
comment|//  RTFCHARS.put("O", "\\'d8");
name|put
argument_list|(
literal|"o"
argument_list|,
literal|"\\'f8"
argument_list|)
expr_stmt|;
name|put
argument_list|(
literal|"'Y"
argument_list|,
literal|"\\u221Y"
argument_list|)
expr_stmt|;
comment|// "Yacute"
name|put
argument_list|(
literal|"TH"
argument_list|,
literal|"\\u222TH"
argument_list|)
expr_stmt|;
comment|// "THORN"
name|put
argument_list|(
literal|"ss"
argument_list|,
literal|"\\u223ss"
argument_list|)
expr_stmt|;
comment|// "szlig"
comment|//RTFCHARS.put("ss", "AFFEN"); // "szlig"
name|put
argument_list|(
literal|"~a"
argument_list|,
literal|"\\u227a"
argument_list|)
expr_stmt|;
comment|// "atilde"
name|put
argument_list|(
literal|"aa"
argument_list|,
literal|"\\u229a"
argument_list|)
expr_stmt|;
comment|// "aring"
comment|//  RTFCHARS.put("ae", "{\\uc2\\u230ae}"); // "aelig" \\u230e6
name|put
argument_list|(
literal|"ae"
argument_list|,
literal|"{\\u230ae}"
argument_list|)
expr_stmt|;
comment|// "aelig" \\u230e6
name|put
argument_list|(
literal|"cc"
argument_list|,
literal|"\\u231c"
argument_list|)
expr_stmt|;
comment|// "ccedil"
name|put
argument_list|(
literal|"dh"
argument_list|,
literal|"\\u240d"
argument_list|)
expr_stmt|;
comment|// "eth"
name|put
argument_list|(
literal|"~o"
argument_list|,
literal|"\\u245o"
argument_list|)
expr_stmt|;
comment|// "otilde"
comment|// According to ISO 8859-1 the "\div" symbol should be placed here
comment|// (#247).
comment|// Omitting this, because it is a mathematical symbol.
name|put
argument_list|(
literal|"o"
argument_list|,
literal|"\\u248o"
argument_list|)
expr_stmt|;
comment|// "oslash"
comment|// RTFCHARS.put("\"u", "\\u252"); // "uuml" exists in standard
comment|// codepage
name|put
argument_list|(
literal|"'y"
argument_list|,
literal|"\\u253y"
argument_list|)
expr_stmt|;
comment|// "yacute"
name|put
argument_list|(
literal|"th"
argument_list|,
literal|"\\u254th"
argument_list|)
expr_stmt|;
comment|// "thorn"
name|put
argument_list|(
literal|"\"y"
argument_list|,
literal|"\\u255y"
argument_list|)
expr_stmt|;
comment|// "yuml"
name|put
argument_list|(
literal|"=A"
argument_list|,
literal|"\\u256A"
argument_list|)
expr_stmt|;
comment|// "Amacr"
name|put
argument_list|(
literal|"=a"
argument_list|,
literal|"\\u257a"
argument_list|)
expr_stmt|;
comment|// "amacr"
name|put
argument_list|(
literal|"uA"
argument_list|,
literal|"\\u258A"
argument_list|)
expr_stmt|;
comment|// "Abreve"
name|put
argument_list|(
literal|"ua"
argument_list|,
literal|"\\u259a"
argument_list|)
expr_stmt|;
comment|// "abreve"
name|put
argument_list|(
literal|"kA"
argument_list|,
literal|"\\u260A"
argument_list|)
expr_stmt|;
comment|// "Aogon"
name|put
argument_list|(
literal|"ka"
argument_list|,
literal|"\\u261a"
argument_list|)
expr_stmt|;
comment|// "aogon"
name|put
argument_list|(
literal|"'C"
argument_list|,
literal|"\\u262C"
argument_list|)
expr_stmt|;
comment|// "Cacute"
name|put
argument_list|(
literal|"'c"
argument_list|,
literal|"\\u263c"
argument_list|)
expr_stmt|;
comment|// "cacute"
name|put
argument_list|(
literal|"^C"
argument_list|,
literal|"\\u264C"
argument_list|)
expr_stmt|;
comment|// "Ccirc"
name|put
argument_list|(
literal|"^c"
argument_list|,
literal|"\\u265c"
argument_list|)
expr_stmt|;
comment|// "ccirc"
name|put
argument_list|(
literal|".C"
argument_list|,
literal|"\\u266C"
argument_list|)
expr_stmt|;
comment|// "Cdot"
name|put
argument_list|(
literal|".c"
argument_list|,
literal|"\\u267c"
argument_list|)
expr_stmt|;
comment|// "cdot"
name|put
argument_list|(
literal|"vC"
argument_list|,
literal|"\\u268C"
argument_list|)
expr_stmt|;
comment|// "Ccaron"
name|put
argument_list|(
literal|"vc"
argument_list|,
literal|"\\u269c"
argument_list|)
expr_stmt|;
comment|// "ccaron"
name|put
argument_list|(
literal|"vD"
argument_list|,
literal|"\\u270D"
argument_list|)
expr_stmt|;
comment|// "Dcaron"
comment|// Symbol #271 (d) has no special Latex command
name|put
argument_list|(
literal|"DJ"
argument_list|,
literal|"\\u272D"
argument_list|)
expr_stmt|;
comment|// "Dstrok"
name|put
argument_list|(
literal|"dj"
argument_list|,
literal|"\\u273d"
argument_list|)
expr_stmt|;
comment|// "dstrok"
name|put
argument_list|(
literal|"=E"
argument_list|,
literal|"\\u274E"
argument_list|)
expr_stmt|;
comment|// "Emacr"
name|put
argument_list|(
literal|"=e"
argument_list|,
literal|"\\u275e"
argument_list|)
expr_stmt|;
comment|// "emacr"
name|put
argument_list|(
literal|"uE"
argument_list|,
literal|"\\u276E"
argument_list|)
expr_stmt|;
comment|// "Ebreve"
name|put
argument_list|(
literal|"ue"
argument_list|,
literal|"\\u277e"
argument_list|)
expr_stmt|;
comment|// "ebreve"
name|put
argument_list|(
literal|".E"
argument_list|,
literal|"\\u278E"
argument_list|)
expr_stmt|;
comment|// "Edot"
name|put
argument_list|(
literal|".e"
argument_list|,
literal|"\\u279e"
argument_list|)
expr_stmt|;
comment|// "edot"
name|put
argument_list|(
literal|"kE"
argument_list|,
literal|"\\u280E"
argument_list|)
expr_stmt|;
comment|// "Eogon"
name|put
argument_list|(
literal|"ke"
argument_list|,
literal|"\\u281e"
argument_list|)
expr_stmt|;
comment|// "eogon"
name|put
argument_list|(
literal|"vE"
argument_list|,
literal|"\\u282E"
argument_list|)
expr_stmt|;
comment|// "Ecaron"
name|put
argument_list|(
literal|"ve"
argument_list|,
literal|"\\u283e"
argument_list|)
expr_stmt|;
comment|// "ecaron"
name|put
argument_list|(
literal|"^G"
argument_list|,
literal|"\\u284G"
argument_list|)
expr_stmt|;
comment|// "Gcirc"
name|put
argument_list|(
literal|"^g"
argument_list|,
literal|"\\u285g"
argument_list|)
expr_stmt|;
comment|// "gcirc"
name|put
argument_list|(
literal|"uG"
argument_list|,
literal|"\\u286G"
argument_list|)
expr_stmt|;
comment|// "Gbreve"
name|put
argument_list|(
literal|"ug"
argument_list|,
literal|"\\u287g"
argument_list|)
expr_stmt|;
comment|// "gbreve"
name|put
argument_list|(
literal|".G"
argument_list|,
literal|"\\u288G"
argument_list|)
expr_stmt|;
comment|// "Gdot"
name|put
argument_list|(
literal|".g"
argument_list|,
literal|"\\u289g"
argument_list|)
expr_stmt|;
comment|// "gdot"
name|put
argument_list|(
literal|"cG"
argument_list|,
literal|"\\u290G"
argument_list|)
expr_stmt|;
comment|// "Gcedil"
name|put
argument_list|(
literal|"'g"
argument_list|,
literal|"\\u291g"
argument_list|)
expr_stmt|;
comment|// "gacute"
name|put
argument_list|(
literal|"^H"
argument_list|,
literal|"\\u292H"
argument_list|)
expr_stmt|;
comment|// "Hcirc"
name|put
argument_list|(
literal|"^h"
argument_list|,
literal|"\\u293h"
argument_list|)
expr_stmt|;
comment|// "hcirc"
name|put
argument_list|(
literal|"Hstrok"
argument_list|,
literal|"\\u294H"
argument_list|)
expr_stmt|;
comment|// "Hstrok"
name|put
argument_list|(
literal|"hstrok"
argument_list|,
literal|"\\u295h"
argument_list|)
expr_stmt|;
comment|// "hstrok"
name|put
argument_list|(
literal|"~I"
argument_list|,
literal|"\\u296I"
argument_list|)
expr_stmt|;
comment|// "Itilde"
name|put
argument_list|(
literal|"~i"
argument_list|,
literal|"\\u297i"
argument_list|)
expr_stmt|;
comment|// "itilde"
name|put
argument_list|(
literal|"=I"
argument_list|,
literal|"\\u298I"
argument_list|)
expr_stmt|;
comment|// "Imacr"
name|put
argument_list|(
literal|"=i"
argument_list|,
literal|"\\u299i"
argument_list|)
expr_stmt|;
comment|// "imacr"
name|put
argument_list|(
literal|"uI"
argument_list|,
literal|"\\u300I"
argument_list|)
expr_stmt|;
comment|// "Ibreve"
name|put
argument_list|(
literal|"ui"
argument_list|,
literal|"\\u301i"
argument_list|)
expr_stmt|;
comment|// "ibreve"
name|put
argument_list|(
literal|"kI"
argument_list|,
literal|"\\u302I"
argument_list|)
expr_stmt|;
comment|// "Iogon"
name|put
argument_list|(
literal|"ki"
argument_list|,
literal|"\\u303i"
argument_list|)
expr_stmt|;
comment|// "iogon"
name|put
argument_list|(
literal|".I"
argument_list|,
literal|"\\u304I"
argument_list|)
expr_stmt|;
comment|// "Idot"
comment|// Symbol #306 (IJ) has no special Latex command
comment|// Symbol #307 (ij) has no special Latex command
name|put
argument_list|(
literal|"^J"
argument_list|,
literal|"\\u308J"
argument_list|)
expr_stmt|;
comment|// "Jcirc"
name|put
argument_list|(
literal|"^j"
argument_list|,
literal|"\\u309j"
argument_list|)
expr_stmt|;
comment|// "jcirc"
name|put
argument_list|(
literal|"cK"
argument_list|,
literal|"\\u310K"
argument_list|)
expr_stmt|;
comment|// "Kcedil"
name|put
argument_list|(
literal|"ck"
argument_list|,
literal|"\\u311k"
argument_list|)
expr_stmt|;
comment|// "kcedil"
comment|// Symbol #312 (k) has no special Latex command
name|put
argument_list|(
literal|"'L"
argument_list|,
literal|"\\u313L"
argument_list|)
expr_stmt|;
comment|// "Lacute"
name|put
argument_list|(
literal|"'l"
argument_list|,
literal|"\\u314l"
argument_list|)
expr_stmt|;
comment|// "lacute"
name|put
argument_list|(
literal|"cL"
argument_list|,
literal|"\\u315L"
argument_list|)
expr_stmt|;
comment|// "Lcedil"
name|put
argument_list|(
literal|"cl"
argument_list|,
literal|"\\u316l"
argument_list|)
expr_stmt|;
comment|// "lcedil"
comment|// Symbol #317 (L) has no special Latex command
comment|// Symbol #318 (l) has no special Latex command
name|put
argument_list|(
literal|"Lmidot"
argument_list|,
literal|"\\u319L"
argument_list|)
expr_stmt|;
comment|// "Lmidot"
name|put
argument_list|(
literal|"lmidot"
argument_list|,
literal|"\\u320l"
argument_list|)
expr_stmt|;
comment|// "lmidot"
name|put
argument_list|(
literal|"L"
argument_list|,
literal|"\\u321L"
argument_list|)
expr_stmt|;
comment|// "Lstrok"
name|put
argument_list|(
literal|"l"
argument_list|,
literal|"\\u322l"
argument_list|)
expr_stmt|;
comment|// "lstrok"
name|put
argument_list|(
literal|"'N"
argument_list|,
literal|"\\u323N"
argument_list|)
expr_stmt|;
comment|// "Nacute"
name|put
argument_list|(
literal|"'n"
argument_list|,
literal|"\\u324n"
argument_list|)
expr_stmt|;
comment|// "nacute"
name|put
argument_list|(
literal|"cN"
argument_list|,
literal|"\\u325N"
argument_list|)
expr_stmt|;
comment|// "Ncedil"
name|put
argument_list|(
literal|"cn"
argument_list|,
literal|"\\u326n"
argument_list|)
expr_stmt|;
comment|// "ncedil"
name|put
argument_list|(
literal|"vN"
argument_list|,
literal|"\\u327N"
argument_list|)
expr_stmt|;
comment|// "Ncaron"
name|put
argument_list|(
literal|"vn"
argument_list|,
literal|"\\u328n"
argument_list|)
expr_stmt|;
comment|// "ncaron"
comment|// Symbol #329 (n) has no special Latex command
name|put
argument_list|(
literal|"NG"
argument_list|,
literal|"\\u330G"
argument_list|)
expr_stmt|;
comment|// "ENG"
name|put
argument_list|(
literal|"ng"
argument_list|,
literal|"\\u331g"
argument_list|)
expr_stmt|;
comment|// "eng"
name|put
argument_list|(
literal|"=O"
argument_list|,
literal|"\\u332O"
argument_list|)
expr_stmt|;
comment|// "Omacr"
name|put
argument_list|(
literal|"=o"
argument_list|,
literal|"\\u333o"
argument_list|)
expr_stmt|;
comment|// "omacr"
name|put
argument_list|(
literal|"uO"
argument_list|,
literal|"\\u334O"
argument_list|)
expr_stmt|;
comment|// "Obreve"
name|put
argument_list|(
literal|"uo"
argument_list|,
literal|"\\u335o"
argument_list|)
expr_stmt|;
comment|// "obreve"
name|put
argument_list|(
literal|"HO"
argument_list|,
literal|"\\u336?"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|put
argument_list|(
literal|"Ho"
argument_list|,
literal|"\\u337?"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|put
argument_list|(
literal|"OE"
argument_list|,
literal|"{\\u338OE}"
argument_list|)
expr_stmt|;
comment|// "OElig"
name|put
argument_list|(
literal|"oe"
argument_list|,
literal|"{\\u339oe}"
argument_list|)
expr_stmt|;
comment|// "oelig"
name|put
argument_list|(
literal|"'R"
argument_list|,
literal|"\\u340R"
argument_list|)
expr_stmt|;
comment|// "Racute"
name|put
argument_list|(
literal|"'r"
argument_list|,
literal|"\\u341r"
argument_list|)
expr_stmt|;
comment|// "racute"
name|put
argument_list|(
literal|"cR"
argument_list|,
literal|"\\u342R"
argument_list|)
expr_stmt|;
comment|// "Rcedil"
name|put
argument_list|(
literal|"cr"
argument_list|,
literal|"\\u343r"
argument_list|)
expr_stmt|;
comment|// "rcedil"
name|put
argument_list|(
literal|"vR"
argument_list|,
literal|"\\u344R"
argument_list|)
expr_stmt|;
comment|// "Rcaron"
name|put
argument_list|(
literal|"vr"
argument_list|,
literal|"\\u345r"
argument_list|)
expr_stmt|;
comment|// "rcaron"
name|put
argument_list|(
literal|"'S"
argument_list|,
literal|"\\u346S"
argument_list|)
expr_stmt|;
comment|// "Sacute"
name|put
argument_list|(
literal|"'s"
argument_list|,
literal|"\\u347s"
argument_list|)
expr_stmt|;
comment|// "sacute"
name|put
argument_list|(
literal|"^S"
argument_list|,
literal|"\\u348S"
argument_list|)
expr_stmt|;
comment|// "Scirc"
name|put
argument_list|(
literal|"^s"
argument_list|,
literal|"\\u349s"
argument_list|)
expr_stmt|;
comment|// "scirc"
name|put
argument_list|(
literal|"cS"
argument_list|,
literal|"\\u350S"
argument_list|)
expr_stmt|;
comment|// "Scedil"
name|put
argument_list|(
literal|"cs"
argument_list|,
literal|"\\u351s"
argument_list|)
expr_stmt|;
comment|// "scedil"
name|put
argument_list|(
literal|"vS"
argument_list|,
literal|"\\u352S"
argument_list|)
expr_stmt|;
comment|// "Scaron"
name|put
argument_list|(
literal|"vs"
argument_list|,
literal|"\\u353s"
argument_list|)
expr_stmt|;
comment|// "scaron"
name|put
argument_list|(
literal|"cT"
argument_list|,
literal|"\\u354T"
argument_list|)
expr_stmt|;
comment|// "Tcedil"
name|put
argument_list|(
literal|"ct"
argument_list|,
literal|"\\u355t"
argument_list|)
expr_stmt|;
comment|// "tcedil"
name|put
argument_list|(
literal|"vT"
argument_list|,
literal|"\\u356T"
argument_list|)
expr_stmt|;
comment|// "Tcaron"
comment|// Symbol #357 (t) has no special Latex command
name|put
argument_list|(
literal|"Tstrok"
argument_list|,
literal|"\\u358T"
argument_list|)
expr_stmt|;
comment|// "Tstrok"
name|put
argument_list|(
literal|"tstrok"
argument_list|,
literal|"\\u359t"
argument_list|)
expr_stmt|;
comment|// "tstrok"
name|put
argument_list|(
literal|"~U"
argument_list|,
literal|"\\u360U"
argument_list|)
expr_stmt|;
comment|// "Utilde"
name|put
argument_list|(
literal|"~u"
argument_list|,
literal|"\\u361u"
argument_list|)
expr_stmt|;
comment|// "utilde"
name|put
argument_list|(
literal|"=U"
argument_list|,
literal|"\\u362U"
argument_list|)
expr_stmt|;
comment|// "Umacr"
name|put
argument_list|(
literal|"=u"
argument_list|,
literal|"\\u363u"
argument_list|)
expr_stmt|;
comment|// "umacr"
name|put
argument_list|(
literal|"uU"
argument_list|,
literal|"\\u364U"
argument_list|)
expr_stmt|;
comment|// "Ubreve"
name|put
argument_list|(
literal|"uu"
argument_list|,
literal|"\\u365u"
argument_list|)
expr_stmt|;
comment|// "ubreve"
name|put
argument_list|(
literal|"rU"
argument_list|,
literal|"\\u366U"
argument_list|)
expr_stmt|;
comment|// "Uring"
name|put
argument_list|(
literal|"ru"
argument_list|,
literal|"\\u367u"
argument_list|)
expr_stmt|;
comment|// "uring"
name|put
argument_list|(
literal|"HU"
argument_list|,
literal|"\\u368?"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|put
argument_list|(
literal|"Hu"
argument_list|,
literal|"\\u369?"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|put
argument_list|(
literal|"kU"
argument_list|,
literal|"\\u370U"
argument_list|)
expr_stmt|;
comment|// "Uogon"
name|put
argument_list|(
literal|"ku"
argument_list|,
literal|"\\u371u"
argument_list|)
expr_stmt|;
comment|// "uogon"
name|put
argument_list|(
literal|"^W"
argument_list|,
literal|"\\u372W"
argument_list|)
expr_stmt|;
comment|// "Wcirc"
name|put
argument_list|(
literal|"^w"
argument_list|,
literal|"\\u373w"
argument_list|)
expr_stmt|;
comment|// "wcirc"
name|put
argument_list|(
literal|"^Y"
argument_list|,
literal|"\\u374Y"
argument_list|)
expr_stmt|;
comment|// "Ycirc"
name|put
argument_list|(
literal|"^y"
argument_list|,
literal|"\\u375y"
argument_list|)
expr_stmt|;
comment|// "ycirc"
name|put
argument_list|(
literal|"\"Y"
argument_list|,
literal|"\\u376Y"
argument_list|)
expr_stmt|;
comment|// "Yuml"
name|put
argument_list|(
literal|"'Z"
argument_list|,
literal|"\\u377Z"
argument_list|)
expr_stmt|;
comment|// "Zacute"
name|put
argument_list|(
literal|"'z"
argument_list|,
literal|"\\u378z"
argument_list|)
expr_stmt|;
comment|// "zacute"
name|put
argument_list|(
literal|".Z"
argument_list|,
literal|"\\u379Z"
argument_list|)
expr_stmt|;
comment|// "Zdot"
name|put
argument_list|(
literal|".z"
argument_list|,
literal|"\\u380z"
argument_list|)
expr_stmt|;
comment|// "zdot"
name|put
argument_list|(
literal|"vZ"
argument_list|,
literal|"\\u381Z"
argument_list|)
expr_stmt|;
comment|// "Zcaron"
name|put
argument_list|(
literal|"vz"
argument_list|,
literal|"\\u382z"
argument_list|)
expr_stmt|;
comment|// "zcaron"
comment|// Symbol #383 (f) has no special Latex command
block|}
DECL|method|put (String key, String value)
specifier|private
name|void
name|put
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|rtfMap
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|get (String key)
specifier|public
name|String
name|get
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|rtfMap
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
block|}
end_class

end_unit

