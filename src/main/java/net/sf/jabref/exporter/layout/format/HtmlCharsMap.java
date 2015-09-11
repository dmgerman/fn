begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|format
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
DECL|class|HtmlCharsMap
class|class
name|HtmlCharsMap
extends|extends
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|method|HtmlCharsMap ()
specifier|public
name|HtmlCharsMap
parameter_list|()
block|{
comment|// HTML named entities from #192 - #255 (UNICODE Latin-1)
name|put
argument_list|(
literal|"`A"
argument_list|,
literal|"&Agrave;"
argument_list|)
expr_stmt|;
comment|// #192
name|put
argument_list|(
literal|"'A"
argument_list|,
literal|"&Aacute;"
argument_list|)
expr_stmt|;
comment|// #193
name|put
argument_list|(
literal|"^A"
argument_list|,
literal|"&Acirc;"
argument_list|)
expr_stmt|;
comment|// #194
name|put
argument_list|(
literal|"~A"
argument_list|,
literal|"&Atilde;"
argument_list|)
expr_stmt|;
comment|// #195
name|put
argument_list|(
literal|"\"A"
argument_list|,
literal|"&Auml;"
argument_list|)
expr_stmt|;
comment|// #196
name|put
argument_list|(
literal|"AA"
argument_list|,
literal|"&Aring;"
argument_list|)
expr_stmt|;
comment|// #197
name|put
argument_list|(
literal|"AE"
argument_list|,
literal|"&AElig;"
argument_list|)
expr_stmt|;
comment|// #198
name|put
argument_list|(
literal|"cC"
argument_list|,
literal|"&Ccedil;"
argument_list|)
expr_stmt|;
comment|// #199
name|put
argument_list|(
literal|"`E"
argument_list|,
literal|"&Egrave;"
argument_list|)
expr_stmt|;
comment|// #200
name|put
argument_list|(
literal|"'E"
argument_list|,
literal|"&Eacute;"
argument_list|)
expr_stmt|;
comment|// #201
name|put
argument_list|(
literal|"^E"
argument_list|,
literal|"&Ecirc;"
argument_list|)
expr_stmt|;
comment|// #202
name|put
argument_list|(
literal|"\"E"
argument_list|,
literal|"&Euml;"
argument_list|)
expr_stmt|;
comment|// #203
name|put
argument_list|(
literal|"`I"
argument_list|,
literal|"&Igrave;"
argument_list|)
expr_stmt|;
comment|// #204
name|put
argument_list|(
literal|"'I"
argument_list|,
literal|"&Iacute;"
argument_list|)
expr_stmt|;
comment|// #205
name|put
argument_list|(
literal|"^I"
argument_list|,
literal|"&Icirc;"
argument_list|)
expr_stmt|;
comment|// #206
name|put
argument_list|(
literal|"\"I"
argument_list|,
literal|"&Iuml;"
argument_list|)
expr_stmt|;
comment|// #207
name|put
argument_list|(
literal|"DH"
argument_list|,
literal|"&ETH;"
argument_list|)
expr_stmt|;
comment|// #208
name|put
argument_list|(
literal|"~N"
argument_list|,
literal|"&Ntilde;"
argument_list|)
expr_stmt|;
comment|// #209
name|put
argument_list|(
literal|"`O"
argument_list|,
literal|"&Ograve;"
argument_list|)
expr_stmt|;
comment|// #210
name|put
argument_list|(
literal|"'O"
argument_list|,
literal|"&Oacute;"
argument_list|)
expr_stmt|;
comment|// #211
name|put
argument_list|(
literal|"^O"
argument_list|,
literal|"&Ocirc;"
argument_list|)
expr_stmt|;
comment|// #212
name|put
argument_list|(
literal|"~O"
argument_list|,
literal|"&Otilde;"
argument_list|)
expr_stmt|;
comment|// #213
name|put
argument_list|(
literal|"\"O"
argument_list|,
literal|"&Ouml;"
argument_list|)
expr_stmt|;
comment|// #214
comment|// According to ISO 8859-1 the "\times" symbol should be placed here
comment|// (#215).
comment|// Omitting this, because it is a mathematical symbol.
name|put
argument_list|(
literal|"O"
argument_list|,
literal|"&Oslash;"
argument_list|)
expr_stmt|;
comment|// #216
name|put
argument_list|(
literal|"`U"
argument_list|,
literal|"&Ugrave;"
argument_list|)
expr_stmt|;
comment|// #217
name|put
argument_list|(
literal|"'U"
argument_list|,
literal|"&Uacute;"
argument_list|)
expr_stmt|;
comment|// #218
name|put
argument_list|(
literal|"^U"
argument_list|,
literal|"&Ucirc;"
argument_list|)
expr_stmt|;
comment|// #219
name|put
argument_list|(
literal|"\"U"
argument_list|,
literal|"&Uuml;"
argument_list|)
expr_stmt|;
comment|// #220
name|put
argument_list|(
literal|"'Y"
argument_list|,
literal|"&Yacute;"
argument_list|)
expr_stmt|;
comment|// #221
name|put
argument_list|(
literal|"TH"
argument_list|,
literal|"&THORN;"
argument_list|)
expr_stmt|;
comment|// #222
name|put
argument_list|(
literal|"ss"
argument_list|,
literal|"&szlig;"
argument_list|)
expr_stmt|;
comment|// #223
name|put
argument_list|(
literal|"`a"
argument_list|,
literal|"&agrave;"
argument_list|)
expr_stmt|;
comment|// #224
name|put
argument_list|(
literal|"'a"
argument_list|,
literal|"&aacute;"
argument_list|)
expr_stmt|;
comment|// #225
name|put
argument_list|(
literal|"^a"
argument_list|,
literal|"&acirc;"
argument_list|)
expr_stmt|;
comment|// #226
name|put
argument_list|(
literal|"~a"
argument_list|,
literal|"&atilde;"
argument_list|)
expr_stmt|;
comment|// #227
name|put
argument_list|(
literal|"\"a"
argument_list|,
literal|"&auml;"
argument_list|)
expr_stmt|;
comment|// #228
name|put
argument_list|(
literal|"aa"
argument_list|,
literal|"&aring;"
argument_list|)
expr_stmt|;
comment|// #229
name|put
argument_list|(
literal|"ae"
argument_list|,
literal|"&aelig;"
argument_list|)
expr_stmt|;
comment|// #230
name|put
argument_list|(
literal|"cc"
argument_list|,
literal|"&ccedil;"
argument_list|)
expr_stmt|;
comment|// #231
name|put
argument_list|(
literal|"`e"
argument_list|,
literal|"&egrave;"
argument_list|)
expr_stmt|;
comment|// #232
name|put
argument_list|(
literal|"'e"
argument_list|,
literal|"&eacute;"
argument_list|)
expr_stmt|;
comment|// #233
name|put
argument_list|(
literal|"^e"
argument_list|,
literal|"&ecirc;"
argument_list|)
expr_stmt|;
comment|// #234
name|put
argument_list|(
literal|"\"e"
argument_list|,
literal|"&euml;"
argument_list|)
expr_stmt|;
comment|// #235
name|put
argument_list|(
literal|"`i"
argument_list|,
literal|"&igrave;"
argument_list|)
expr_stmt|;
comment|// #236
name|put
argument_list|(
literal|"'i"
argument_list|,
literal|"&iacute;"
argument_list|)
expr_stmt|;
comment|// #237
name|put
argument_list|(
literal|"^i"
argument_list|,
literal|"&icirc;"
argument_list|)
expr_stmt|;
comment|// #238
name|put
argument_list|(
literal|"\"i"
argument_list|,
literal|"&iuml;"
argument_list|)
expr_stmt|;
comment|// #239
name|put
argument_list|(
literal|"dh"
argument_list|,
literal|"&eth;"
argument_list|)
expr_stmt|;
comment|// #240
name|put
argument_list|(
literal|"~n"
argument_list|,
literal|"&ntilde;"
argument_list|)
expr_stmt|;
comment|// #241
name|put
argument_list|(
literal|"`o"
argument_list|,
literal|"&ograve;"
argument_list|)
expr_stmt|;
comment|// #242
name|put
argument_list|(
literal|"'o"
argument_list|,
literal|"&oacute;"
argument_list|)
expr_stmt|;
comment|// #243
name|put
argument_list|(
literal|"^o"
argument_list|,
literal|"&ocirc;"
argument_list|)
expr_stmt|;
comment|// #244
name|put
argument_list|(
literal|"~o"
argument_list|,
literal|"&otilde;"
argument_list|)
expr_stmt|;
comment|// #245
name|put
argument_list|(
literal|"\"o"
argument_list|,
literal|"&ouml;"
argument_list|)
expr_stmt|;
comment|// #246
comment|// According to ISO 8859-1 the "\div" symbol should be placed here
comment|// (#247).
comment|// Omitting this, because it is a mathematical symbol.
name|put
argument_list|(
literal|"o"
argument_list|,
literal|"&oslash;"
argument_list|)
expr_stmt|;
comment|// #248
name|put
argument_list|(
literal|"`u"
argument_list|,
literal|"&ugrave;"
argument_list|)
expr_stmt|;
comment|// #249
name|put
argument_list|(
literal|"'u"
argument_list|,
literal|"&uacute;"
argument_list|)
expr_stmt|;
comment|// #250
name|put
argument_list|(
literal|"^u"
argument_list|,
literal|"&ucirc;"
argument_list|)
expr_stmt|;
comment|// #251
name|put
argument_list|(
literal|"\"u"
argument_list|,
literal|"&uuml;"
argument_list|)
expr_stmt|;
comment|// #252
name|put
argument_list|(
literal|"'y"
argument_list|,
literal|"&yacute;"
argument_list|)
expr_stmt|;
comment|// #253
name|put
argument_list|(
literal|"th"
argument_list|,
literal|"&thorn;"
argument_list|)
expr_stmt|;
comment|// #254
name|put
argument_list|(
literal|"\"y"
argument_list|,
literal|"&yuml;"
argument_list|)
expr_stmt|;
comment|// #255
comment|// HTML special characters without names (UNICODE Latin Extended-A),
comment|// indicated by UNICODE number
name|put
argument_list|(
literal|"=A"
argument_list|,
literal|"&#256;"
argument_list|)
expr_stmt|;
comment|// "Amacr"
name|put
argument_list|(
literal|"=a"
argument_list|,
literal|"&#257;"
argument_list|)
expr_stmt|;
comment|// "amacr"
name|put
argument_list|(
literal|"uA"
argument_list|,
literal|"&#258;"
argument_list|)
expr_stmt|;
comment|// "Abreve"
name|put
argument_list|(
literal|"ua"
argument_list|,
literal|"&#259;"
argument_list|)
expr_stmt|;
comment|// "abreve"
name|put
argument_list|(
literal|"kA"
argument_list|,
literal|"&#260;"
argument_list|)
expr_stmt|;
comment|// "Aogon"
name|put
argument_list|(
literal|"ka"
argument_list|,
literal|"&#261;"
argument_list|)
expr_stmt|;
comment|// "aogon"
name|put
argument_list|(
literal|"'C"
argument_list|,
literal|"&#262;"
argument_list|)
expr_stmt|;
comment|// "Cacute"
name|put
argument_list|(
literal|"'c"
argument_list|,
literal|"&#263;"
argument_list|)
expr_stmt|;
comment|// "cacute"
name|put
argument_list|(
literal|"^C"
argument_list|,
literal|"&#264;"
argument_list|)
expr_stmt|;
comment|// "Ccirc"
name|put
argument_list|(
literal|"^c"
argument_list|,
literal|"&#265;"
argument_list|)
expr_stmt|;
comment|// "ccirc"
name|put
argument_list|(
literal|".C"
argument_list|,
literal|"&#266;"
argument_list|)
expr_stmt|;
comment|// "Cdot"
name|put
argument_list|(
literal|".c"
argument_list|,
literal|"&#267;"
argument_list|)
expr_stmt|;
comment|// "cdot"
name|put
argument_list|(
literal|"vC"
argument_list|,
literal|"&#268;"
argument_list|)
expr_stmt|;
comment|// "Ccaron"
name|put
argument_list|(
literal|"vc"
argument_list|,
literal|"&#269;"
argument_list|)
expr_stmt|;
comment|// "ccaron"
name|put
argument_list|(
literal|"vD"
argument_list|,
literal|"&#270;"
argument_list|)
expr_stmt|;
comment|// "Dcaron"
comment|// Symbol #271 (d) has no special Latex command
name|put
argument_list|(
literal|"DJ"
argument_list|,
literal|"&#272;"
argument_list|)
expr_stmt|;
comment|// "Dstrok"
name|put
argument_list|(
literal|"dj"
argument_list|,
literal|"&#273;"
argument_list|)
expr_stmt|;
comment|// "dstrok"
name|put
argument_list|(
literal|"=E"
argument_list|,
literal|"&#274;"
argument_list|)
expr_stmt|;
comment|// "Emacr"
name|put
argument_list|(
literal|"=e"
argument_list|,
literal|"&#275;"
argument_list|)
expr_stmt|;
comment|// "emacr"
name|put
argument_list|(
literal|"uE"
argument_list|,
literal|"&#276;"
argument_list|)
expr_stmt|;
comment|// "Ebreve"
name|put
argument_list|(
literal|"ue"
argument_list|,
literal|"&#277;"
argument_list|)
expr_stmt|;
comment|// "ebreve"
name|put
argument_list|(
literal|".E"
argument_list|,
literal|"&#278;"
argument_list|)
expr_stmt|;
comment|// "Edot"
name|put
argument_list|(
literal|".e"
argument_list|,
literal|"&#279;"
argument_list|)
expr_stmt|;
comment|// "edot"
name|put
argument_list|(
literal|"kE"
argument_list|,
literal|"&#280;"
argument_list|)
expr_stmt|;
comment|// "Eogon"
name|put
argument_list|(
literal|"ke"
argument_list|,
literal|"&#281;"
argument_list|)
expr_stmt|;
comment|// "eogon"
name|put
argument_list|(
literal|"vE"
argument_list|,
literal|"&#282;"
argument_list|)
expr_stmt|;
comment|// "Ecaron"
name|put
argument_list|(
literal|"ve"
argument_list|,
literal|"&#283;"
argument_list|)
expr_stmt|;
comment|// "ecaron"
name|put
argument_list|(
literal|"^G"
argument_list|,
literal|"&#284;"
argument_list|)
expr_stmt|;
comment|// "Gcirc"
name|put
argument_list|(
literal|"^g"
argument_list|,
literal|"&#285;"
argument_list|)
expr_stmt|;
comment|// "gcirc"
name|put
argument_list|(
literal|"uG"
argument_list|,
literal|"&#286;"
argument_list|)
expr_stmt|;
comment|// "Gbreve"
name|put
argument_list|(
literal|"ug"
argument_list|,
literal|"&#287;"
argument_list|)
expr_stmt|;
comment|// "gbreve"
name|put
argument_list|(
literal|".G"
argument_list|,
literal|"&#288;"
argument_list|)
expr_stmt|;
comment|// "Gdot"
name|put
argument_list|(
literal|".g"
argument_list|,
literal|"&#289;"
argument_list|)
expr_stmt|;
comment|// "gdot"
name|put
argument_list|(
literal|"cG"
argument_list|,
literal|"&#290;"
argument_list|)
expr_stmt|;
comment|// "Gcedil"
name|put
argument_list|(
literal|"'g"
argument_list|,
literal|"&#291;"
argument_list|)
expr_stmt|;
comment|// "gacute"
name|put
argument_list|(
literal|"^H"
argument_list|,
literal|"&#292;"
argument_list|)
expr_stmt|;
comment|// "Hcirc"
name|put
argument_list|(
literal|"^h"
argument_list|,
literal|"&#293;"
argument_list|)
expr_stmt|;
comment|// "hcirc"
name|put
argument_list|(
literal|"Hstrok"
argument_list|,
literal|"&#294;"
argument_list|)
expr_stmt|;
comment|// "Hstrok"
name|put
argument_list|(
literal|"hstrok"
argument_list|,
literal|"&#295;"
argument_list|)
expr_stmt|;
comment|// "hstrok"
name|put
argument_list|(
literal|"~I"
argument_list|,
literal|"&#296;"
argument_list|)
expr_stmt|;
comment|// "Itilde"
name|put
argument_list|(
literal|"~i"
argument_list|,
literal|"&#297;"
argument_list|)
expr_stmt|;
comment|// "itilde"
name|put
argument_list|(
literal|"=I"
argument_list|,
literal|"&#298;"
argument_list|)
expr_stmt|;
comment|// "Imacr"
name|put
argument_list|(
literal|"=i"
argument_list|,
literal|"&#299;"
argument_list|)
expr_stmt|;
comment|// "imacr"
name|put
argument_list|(
literal|"uI"
argument_list|,
literal|"&#300;"
argument_list|)
expr_stmt|;
comment|// "Ibreve"
name|put
argument_list|(
literal|"ui"
argument_list|,
literal|"&#301;"
argument_list|)
expr_stmt|;
comment|// "ibreve"
name|put
argument_list|(
literal|"kI"
argument_list|,
literal|"&#302;"
argument_list|)
expr_stmt|;
comment|// "Iogon"
name|put
argument_list|(
literal|"ki"
argument_list|,
literal|"&#303;"
argument_list|)
expr_stmt|;
comment|// "iogon"
name|put
argument_list|(
literal|".I"
argument_list|,
literal|"&#304;"
argument_list|)
expr_stmt|;
comment|// "Idot"
name|put
argument_list|(
literal|"i"
argument_list|,
literal|"&#305;"
argument_list|)
expr_stmt|;
comment|// "inodot"
comment|// Symbol #306 (IJ) has no special Latex command
comment|// Symbol #307 (ij) has no special Latex command
name|put
argument_list|(
literal|"^J"
argument_list|,
literal|"&#308;"
argument_list|)
expr_stmt|;
comment|// "Jcirc"
name|put
argument_list|(
literal|"^j"
argument_list|,
literal|"&#309;"
argument_list|)
expr_stmt|;
comment|// "jcirc"
name|put
argument_list|(
literal|"cK"
argument_list|,
literal|"&#310;"
argument_list|)
expr_stmt|;
comment|// "Kcedil"
name|put
argument_list|(
literal|"ck"
argument_list|,
literal|"&#311;"
argument_list|)
expr_stmt|;
comment|// "kcedil"
comment|// Symbol #312 (k) has no special Latex command
name|put
argument_list|(
literal|"'L"
argument_list|,
literal|"&#313;"
argument_list|)
expr_stmt|;
comment|// "Lacute"
name|put
argument_list|(
literal|"'l"
argument_list|,
literal|"&#314;"
argument_list|)
expr_stmt|;
comment|// "lacute"
name|put
argument_list|(
literal|"cL"
argument_list|,
literal|"&#315;"
argument_list|)
expr_stmt|;
comment|// "Lcedil"
name|put
argument_list|(
literal|"cl"
argument_list|,
literal|"&#316;"
argument_list|)
expr_stmt|;
comment|// "lcedil"
comment|// Symbol #317 (L) has no special Latex command
comment|// Symbol #318 (l) has no special Latex command
name|put
argument_list|(
literal|"Lmidot"
argument_list|,
literal|"&#319;"
argument_list|)
expr_stmt|;
comment|// "Lmidot"
name|put
argument_list|(
literal|"lmidot"
argument_list|,
literal|"&#320;"
argument_list|)
expr_stmt|;
comment|// "lmidot"
name|put
argument_list|(
literal|"L"
argument_list|,
literal|"&#321;"
argument_list|)
expr_stmt|;
comment|// "Lstrok"
name|put
argument_list|(
literal|"l"
argument_list|,
literal|"&#322;"
argument_list|)
expr_stmt|;
comment|// "lstrok"
name|put
argument_list|(
literal|"'N"
argument_list|,
literal|"&#323;"
argument_list|)
expr_stmt|;
comment|// "Nacute"
name|put
argument_list|(
literal|"'n"
argument_list|,
literal|"&#324;"
argument_list|)
expr_stmt|;
comment|// "nacute"
name|put
argument_list|(
literal|"cN"
argument_list|,
literal|"&#325;"
argument_list|)
expr_stmt|;
comment|// "Ncedil"
name|put
argument_list|(
literal|"cn"
argument_list|,
literal|"&#326;"
argument_list|)
expr_stmt|;
comment|// "ncedil"
name|put
argument_list|(
literal|"vN"
argument_list|,
literal|"&#327;"
argument_list|)
expr_stmt|;
comment|// "Ncaron"
name|put
argument_list|(
literal|"vn"
argument_list|,
literal|"&#328;"
argument_list|)
expr_stmt|;
comment|// "ncaron"
comment|// Symbol #329 (n) has no special Latex command
name|put
argument_list|(
literal|"NG"
argument_list|,
literal|"&#330;"
argument_list|)
expr_stmt|;
comment|// "ENG"
name|put
argument_list|(
literal|"ng"
argument_list|,
literal|"&#331;"
argument_list|)
expr_stmt|;
comment|// "eng"
name|put
argument_list|(
literal|"=O"
argument_list|,
literal|"&#332;"
argument_list|)
expr_stmt|;
comment|// "Omacr"
name|put
argument_list|(
literal|"=o"
argument_list|,
literal|"&#333;"
argument_list|)
expr_stmt|;
comment|// "omacr"
name|put
argument_list|(
literal|"uO"
argument_list|,
literal|"&#334;"
argument_list|)
expr_stmt|;
comment|// "Obreve"
name|put
argument_list|(
literal|"uo"
argument_list|,
literal|"&#335;"
argument_list|)
expr_stmt|;
comment|// "obreve"
name|put
argument_list|(
literal|"HO"
argument_list|,
literal|"&#336;"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|put
argument_list|(
literal|"Ho"
argument_list|,
literal|"&#337;"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|put
argument_list|(
literal|"OE"
argument_list|,
literal|"&#338;"
argument_list|)
expr_stmt|;
comment|// "OElig"
name|put
argument_list|(
literal|"oe"
argument_list|,
literal|"&#339;"
argument_list|)
expr_stmt|;
comment|// "oelig"
name|put
argument_list|(
literal|"'R"
argument_list|,
literal|"&#340;"
argument_list|)
expr_stmt|;
comment|// "Racute"
name|put
argument_list|(
literal|"'r"
argument_list|,
literal|"&#341;"
argument_list|)
expr_stmt|;
comment|// "racute"
name|put
argument_list|(
literal|"cR"
argument_list|,
literal|"&#342;"
argument_list|)
expr_stmt|;
comment|// "Rcedil"
name|put
argument_list|(
literal|"cr"
argument_list|,
literal|"&#343;"
argument_list|)
expr_stmt|;
comment|// "rcedil"
name|put
argument_list|(
literal|"vR"
argument_list|,
literal|"&#344;"
argument_list|)
expr_stmt|;
comment|// "Rcaron"
name|put
argument_list|(
literal|"vr"
argument_list|,
literal|"&#345;"
argument_list|)
expr_stmt|;
comment|// "rcaron"
name|put
argument_list|(
literal|"'S"
argument_list|,
literal|"&#346;"
argument_list|)
expr_stmt|;
comment|// "Sacute"
name|put
argument_list|(
literal|"'s"
argument_list|,
literal|"&#347;"
argument_list|)
expr_stmt|;
comment|// "sacute"
name|put
argument_list|(
literal|"^S"
argument_list|,
literal|"&#348;"
argument_list|)
expr_stmt|;
comment|// "Scirc"
name|put
argument_list|(
literal|"^s"
argument_list|,
literal|"&#349;"
argument_list|)
expr_stmt|;
comment|// "scirc"
name|put
argument_list|(
literal|"cS"
argument_list|,
literal|"&#350;"
argument_list|)
expr_stmt|;
comment|// "Scedil"
name|put
argument_list|(
literal|"cs"
argument_list|,
literal|"&#351;"
argument_list|)
expr_stmt|;
comment|// "scedil"
name|put
argument_list|(
literal|"vS"
argument_list|,
literal|"&#352;"
argument_list|)
expr_stmt|;
comment|// "Scaron"
name|put
argument_list|(
literal|"vs"
argument_list|,
literal|"&#353;"
argument_list|)
expr_stmt|;
comment|// "scaron"
name|put
argument_list|(
literal|"cT"
argument_list|,
literal|"&#354;"
argument_list|)
expr_stmt|;
comment|// "Tcedil"
name|put
argument_list|(
literal|"ct"
argument_list|,
literal|"&#355;"
argument_list|)
expr_stmt|;
comment|// "tcedil"
name|put
argument_list|(
literal|"vT"
argument_list|,
literal|"&#356;"
argument_list|)
expr_stmt|;
comment|// "Tcaron"
comment|// Symbol #357 (t) has no special Latex command
name|put
argument_list|(
literal|"Tstrok"
argument_list|,
literal|"&#358;"
argument_list|)
expr_stmt|;
comment|// "Tstrok"
name|put
argument_list|(
literal|"tstrok"
argument_list|,
literal|"&#359;"
argument_list|)
expr_stmt|;
comment|// "tstrok"
name|put
argument_list|(
literal|"~U"
argument_list|,
literal|"&#360;"
argument_list|)
expr_stmt|;
comment|// "Utilde"
name|put
argument_list|(
literal|"~u"
argument_list|,
literal|"&#361;"
argument_list|)
expr_stmt|;
comment|// "utilde"
name|put
argument_list|(
literal|"=U"
argument_list|,
literal|"&#362;"
argument_list|)
expr_stmt|;
comment|// "Umacr"
name|put
argument_list|(
literal|"=u"
argument_list|,
literal|"&#363;"
argument_list|)
expr_stmt|;
comment|// "umacr"
name|put
argument_list|(
literal|"uU"
argument_list|,
literal|"&#364;"
argument_list|)
expr_stmt|;
comment|// "Ubreve"
name|put
argument_list|(
literal|"uu"
argument_list|,
literal|"&#365;"
argument_list|)
expr_stmt|;
comment|// "ubreve"
name|put
argument_list|(
literal|"rU"
argument_list|,
literal|"&#366;"
argument_list|)
expr_stmt|;
comment|// "Uring"
name|put
argument_list|(
literal|"ru"
argument_list|,
literal|"&#367;"
argument_list|)
expr_stmt|;
comment|// "uring"
name|put
argument_list|(
literal|"HU"
argument_list|,
literal|"&#368;"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|put
argument_list|(
literal|"Hu"
argument_list|,
literal|"&#369;"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|put
argument_list|(
literal|"kU"
argument_list|,
literal|"&#370;"
argument_list|)
expr_stmt|;
comment|// "Uogon"
name|put
argument_list|(
literal|"ku"
argument_list|,
literal|"&#371;"
argument_list|)
expr_stmt|;
comment|// "uogon"
name|put
argument_list|(
literal|"^W"
argument_list|,
literal|"&#372;"
argument_list|)
expr_stmt|;
comment|// "Wcirc"
name|put
argument_list|(
literal|"^w"
argument_list|,
literal|"&#373;"
argument_list|)
expr_stmt|;
comment|// "wcirc"
name|put
argument_list|(
literal|"^Y"
argument_list|,
literal|"&#374;"
argument_list|)
expr_stmt|;
comment|// "Ycirc"
name|put
argument_list|(
literal|"^y"
argument_list|,
literal|"&#375;"
argument_list|)
expr_stmt|;
comment|// "ycirc"
name|put
argument_list|(
literal|"\"Y"
argument_list|,
literal|"&#376;"
argument_list|)
expr_stmt|;
comment|// "Yuml"
name|put
argument_list|(
literal|"'Z"
argument_list|,
literal|"&#377;"
argument_list|)
expr_stmt|;
comment|// "Zacute"
name|put
argument_list|(
literal|"'z"
argument_list|,
literal|"&#378;"
argument_list|)
expr_stmt|;
comment|// "zacute"
name|put
argument_list|(
literal|".Z"
argument_list|,
literal|"&#379;"
argument_list|)
expr_stmt|;
comment|// "Zdot"
name|put
argument_list|(
literal|".z"
argument_list|,
literal|"&#380;"
argument_list|)
expr_stmt|;
comment|// "zdot"
name|put
argument_list|(
literal|"vZ"
argument_list|,
literal|"&#381;"
argument_list|)
expr_stmt|;
comment|// "Zcaron"
name|put
argument_list|(
literal|"vz"
argument_list|,
literal|"&#382;"
argument_list|)
expr_stmt|;
comment|// "zcaron"
comment|// Symbol #383 (f) has no special Latex command
name|put
argument_list|(
literal|"%"
argument_list|,
literal|"%"
argument_list|)
expr_stmt|;
comment|// percent sign
block|}
block|}
end_class

end_unit

