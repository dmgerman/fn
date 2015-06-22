begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
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
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|Util
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_comment
comment|/**  * This formatter converts LaTeX character sequences their equicalent unicode characters,  * and removes other LaTeX commands without handling them.  */
end_comment

begin_class
DECL|class|FormatChars
specifier|public
class|class
name|FormatChars
implements|implements
name|LayoutFormatter
block|{
DECL|field|CHARS
specifier|public
specifier|static
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|CHARS
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
static|static
block|{
name|CHARS
operator|.
name|put
argument_list|(
literal|"`A"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #192
name|CHARS
operator|.
name|put
argument_list|(
literal|"'A"
argument_list|,
literal|"Ã?"
argument_list|)
expr_stmt|;
comment|// #193
name|CHARS
operator|.
name|put
argument_list|(
literal|"^A"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #194
name|CHARS
operator|.
name|put
argument_list|(
literal|"~A"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #195
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"A"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #196
name|CHARS
operator|.
name|put
argument_list|(
literal|"AA"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #197
name|CHARS
operator|.
name|put
argument_list|(
literal|"AE"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #198
name|CHARS
operator|.
name|put
argument_list|(
literal|"cC"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #199
name|CHARS
operator|.
name|put
argument_list|(
literal|"`E"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #200
name|CHARS
operator|.
name|put
argument_list|(
literal|"'E"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #201
name|CHARS
operator|.
name|put
argument_list|(
literal|"^E"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #202
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"E"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #203
name|CHARS
operator|.
name|put
argument_list|(
literal|"`I"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #204
name|CHARS
operator|.
name|put
argument_list|(
literal|"'I"
argument_list|,
literal|"Ã?"
argument_list|)
expr_stmt|;
comment|// #205
name|CHARS
operator|.
name|put
argument_list|(
literal|"^I"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #206
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"I"
argument_list|,
literal|"Ã?"
argument_list|)
expr_stmt|;
comment|// #207
name|CHARS
operator|.
name|put
argument_list|(
literal|"DH"
argument_list|,
literal|"Ã?"
argument_list|)
expr_stmt|;
comment|// #208
name|CHARS
operator|.
name|put
argument_list|(
literal|"~N"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #209
name|CHARS
operator|.
name|put
argument_list|(
literal|"`O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #210
name|CHARS
operator|.
name|put
argument_list|(
literal|"'O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #211
name|CHARS
operator|.
name|put
argument_list|(
literal|"^O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #212
name|CHARS
operator|.
name|put
argument_list|(
literal|"~O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #213
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #214
comment|// According to ISO 8859-1 the "\times" symbol should be placed here
comment|// (#215).
comment|// Omitting this, because it is a mathematical symbol.
name|CHARS
operator|.
name|put
argument_list|(
literal|"O"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #216
name|CHARS
operator|.
name|put
argument_list|(
literal|"`U"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #217
name|CHARS
operator|.
name|put
argument_list|(
literal|"'U"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #218
name|CHARS
operator|.
name|put
argument_list|(
literal|"^U"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #219
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"U"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #220
name|CHARS
operator|.
name|put
argument_list|(
literal|"'Y"
argument_list|,
literal|"Ã?"
argument_list|)
expr_stmt|;
comment|// #221
name|CHARS
operator|.
name|put
argument_list|(
literal|"TH"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #222
name|CHARS
operator|.
name|put
argument_list|(
literal|"ss"
argument_list|,
literal|"Ã"
argument_list|)
expr_stmt|;
comment|// #223
name|CHARS
operator|.
name|put
argument_list|(
literal|"`a"
argument_list|,
literal|"Ã "
argument_list|)
expr_stmt|;
comment|// #224
name|CHARS
operator|.
name|put
argument_list|(
literal|"'a"
argument_list|,
literal|"Ã¡"
argument_list|)
expr_stmt|;
comment|// #225
name|CHARS
operator|.
name|put
argument_list|(
literal|"^a"
argument_list|,
literal|"Ã¢"
argument_list|)
expr_stmt|;
comment|// #226
name|CHARS
operator|.
name|put
argument_list|(
literal|"~a"
argument_list|,
literal|"Ã£"
argument_list|)
expr_stmt|;
comment|// #227
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"a"
argument_list|,
literal|"Ã¤"
argument_list|)
expr_stmt|;
comment|// #228
name|CHARS
operator|.
name|put
argument_list|(
literal|"aa"
argument_list|,
literal|"Ã¥"
argument_list|)
expr_stmt|;
comment|// #229
name|CHARS
operator|.
name|put
argument_list|(
literal|"ae"
argument_list|,
literal|"Ã¦"
argument_list|)
expr_stmt|;
comment|// #230
name|CHARS
operator|.
name|put
argument_list|(
literal|"cc"
argument_list|,
literal|"Ã§"
argument_list|)
expr_stmt|;
comment|// #231
name|CHARS
operator|.
name|put
argument_list|(
literal|"`e"
argument_list|,
literal|"Ã¨"
argument_list|)
expr_stmt|;
comment|// #232
name|CHARS
operator|.
name|put
argument_list|(
literal|"'e"
argument_list|,
literal|"Ã©"
argument_list|)
expr_stmt|;
comment|// #233
name|CHARS
operator|.
name|put
argument_list|(
literal|"^e"
argument_list|,
literal|"Ãª"
argument_list|)
expr_stmt|;
comment|// #234
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"e"
argument_list|,
literal|"Ã«"
argument_list|)
expr_stmt|;
comment|// #235
name|CHARS
operator|.
name|put
argument_list|(
literal|"`i"
argument_list|,
literal|"Ã¬"
argument_list|)
expr_stmt|;
comment|// #236
name|CHARS
operator|.
name|put
argument_list|(
literal|"'i"
argument_list|,
literal|"Ã­"
argument_list|)
expr_stmt|;
comment|// #237
name|CHARS
operator|.
name|put
argument_list|(
literal|"^i"
argument_list|,
literal|"Ã®"
argument_list|)
expr_stmt|;
comment|// #238
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"i"
argument_list|,
literal|"Ã¯"
argument_list|)
expr_stmt|;
comment|// #239
name|CHARS
operator|.
name|put
argument_list|(
literal|"dh"
argument_list|,
literal|"Ã°"
argument_list|)
expr_stmt|;
comment|// #240
name|CHARS
operator|.
name|put
argument_list|(
literal|"~n"
argument_list|,
literal|"Ã±"
argument_list|)
expr_stmt|;
comment|// #241
name|CHARS
operator|.
name|put
argument_list|(
literal|"`o"
argument_list|,
literal|"Ã²"
argument_list|)
expr_stmt|;
comment|// #242
name|CHARS
operator|.
name|put
argument_list|(
literal|"'o"
argument_list|,
literal|"Ã³"
argument_list|)
expr_stmt|;
comment|// #243
name|CHARS
operator|.
name|put
argument_list|(
literal|"^o"
argument_list|,
literal|"Ã´"
argument_list|)
expr_stmt|;
comment|// #244
name|CHARS
operator|.
name|put
argument_list|(
literal|"~o"
argument_list|,
literal|"Ãµ"
argument_list|)
expr_stmt|;
comment|// #245
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"o"
argument_list|,
literal|"Ã¶"
argument_list|)
expr_stmt|;
comment|// #246
comment|// According to ISO 8859-1 the "\div" symbol should be placed here
comment|// (#247).
comment|// Omitting this, because it is a mathematical symbol.
name|CHARS
operator|.
name|put
argument_list|(
literal|"o"
argument_list|,
literal|"Ã¸"
argument_list|)
expr_stmt|;
comment|// #248
name|CHARS
operator|.
name|put
argument_list|(
literal|"`u"
argument_list|,
literal|"Ã¹"
argument_list|)
expr_stmt|;
comment|// #249
name|CHARS
operator|.
name|put
argument_list|(
literal|"'u"
argument_list|,
literal|"Ãº"
argument_list|)
expr_stmt|;
comment|// #250
name|CHARS
operator|.
name|put
argument_list|(
literal|"^u"
argument_list|,
literal|"Ã»"
argument_list|)
expr_stmt|;
comment|// #251
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"u"
argument_list|,
literal|"Ã¼"
argument_list|)
expr_stmt|;
comment|// #252
name|CHARS
operator|.
name|put
argument_list|(
literal|"'y"
argument_list|,
literal|"Ã½"
argument_list|)
expr_stmt|;
comment|// #253
name|CHARS
operator|.
name|put
argument_list|(
literal|"th"
argument_list|,
literal|"Ã¾"
argument_list|)
expr_stmt|;
comment|// #254
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"y"
argument_list|,
literal|"Ã¿"
argument_list|)
expr_stmt|;
comment|// #255
comment|// HTML special characters without names (UNICODE Latin Extended-A),
comment|// indicated by UNICODE number
name|CHARS
operator|.
name|put
argument_list|(
literal|"=A"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Amacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=a"
argument_list|,
literal|"Ä?"
argument_list|)
expr_stmt|;
comment|// "amacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uA"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Abreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ua"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "abreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"kA"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Aogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ka"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "aogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'C"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Cacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'c"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "cacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^C"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Ccirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^c"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "ccirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|".C"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Cdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|".c"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "cdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vC"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Ccaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vc"
argument_list|,
literal|"Ä?"
argument_list|)
expr_stmt|;
comment|// "ccaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vD"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Dcaron"
comment|// Symbol #271 (dï¿½) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"DJ"
argument_list|,
literal|"Ä?"
argument_list|)
expr_stmt|;
comment|// "Dstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"dj"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "dstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=E"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Emacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=e"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "emacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uE"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Ebreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ue"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "ebreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|".E"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Edot"
name|CHARS
operator|.
name|put
argument_list|(
literal|".e"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "edot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"kE"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Eogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ke"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "eogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vE"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Ecaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ve"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "ecaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^G"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Gcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^g"
argument_list|,
literal|"Ä?"
argument_list|)
expr_stmt|;
comment|// "gcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uG"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "Gbreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ug"
argument_list|,
literal|"Ä"
argument_list|)
expr_stmt|;
comment|// "gbreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|".G"
argument_list|,
literal|"Ä "
argument_list|)
expr_stmt|;
comment|// "Gdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|".g"
argument_list|,
literal|"Ä¡"
argument_list|)
expr_stmt|;
comment|// "gdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cG"
argument_list|,
literal|"Ä¢"
argument_list|)
expr_stmt|;
comment|// "Gcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'g"
argument_list|,
literal|"Ä£"
argument_list|)
expr_stmt|;
comment|// "gacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^H"
argument_list|,
literal|"Ä¤"
argument_list|)
expr_stmt|;
comment|// "Hcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^h"
argument_list|,
literal|"Ä¥"
argument_list|)
expr_stmt|;
comment|// "hcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"Hstrok"
argument_list|,
literal|"Ä¦"
argument_list|)
expr_stmt|;
comment|// "Hstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"hstrok"
argument_list|,
literal|"Ä§"
argument_list|)
expr_stmt|;
comment|// "hstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"~I"
argument_list|,
literal|"Ä¨"
argument_list|)
expr_stmt|;
comment|// "Itilde"
name|CHARS
operator|.
name|put
argument_list|(
literal|"~i"
argument_list|,
literal|"Ä©"
argument_list|)
expr_stmt|;
comment|// "itilde"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=I"
argument_list|,
literal|"Äª"
argument_list|)
expr_stmt|;
comment|// "Imacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=i"
argument_list|,
literal|"Ä«"
argument_list|)
expr_stmt|;
comment|// "imacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uI"
argument_list|,
literal|"Ä¬"
argument_list|)
expr_stmt|;
comment|// "Ibreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ui"
argument_list|,
literal|"Ä­"
argument_list|)
expr_stmt|;
comment|// "ibreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"kI"
argument_list|,
literal|"Ä®"
argument_list|)
expr_stmt|;
comment|// "Iogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ki"
argument_list|,
literal|"Ä¯"
argument_list|)
expr_stmt|;
comment|// "iogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|".I"
argument_list|,
literal|"Ä°"
argument_list|)
expr_stmt|;
comment|// "Idot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"i"
argument_list|,
literal|"Ä±"
argument_list|)
expr_stmt|;
comment|// "inodot"
comment|// Symbol #306 (IJ) has no special Latex command
comment|// Symbol #307 (ij) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"^J"
argument_list|,
literal|"Ä´"
argument_list|)
expr_stmt|;
comment|// "Jcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^j"
argument_list|,
literal|"Äµ"
argument_list|)
expr_stmt|;
comment|// "jcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cK"
argument_list|,
literal|"Ä¶"
argument_list|)
expr_stmt|;
comment|// "Kcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ck"
argument_list|,
literal|"Ä·"
argument_list|)
expr_stmt|;
comment|// "kcedil"
comment|// Symbol #312 (k) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"'L"
argument_list|,
literal|"Ä¹"
argument_list|)
expr_stmt|;
comment|// "Lacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'l"
argument_list|,
literal|"Äº"
argument_list|)
expr_stmt|;
comment|// "lacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cL"
argument_list|,
literal|"Ä»"
argument_list|)
expr_stmt|;
comment|// "Lcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cl"
argument_list|,
literal|"Ä¼"
argument_list|)
expr_stmt|;
comment|// "lcedil"
comment|// Symbol #317 (Lï¿½) has no special Latex command
comment|// Symbol #318 (lï¿½) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"Lmidot"
argument_list|,
literal|"Ä¿"
argument_list|)
expr_stmt|;
comment|// "Lmidot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"lmidot"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "lmidot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"L"
argument_list|,
literal|"Å?"
argument_list|)
expr_stmt|;
comment|// "Lstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"l"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "lstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'N"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Nacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'n"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "nacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cN"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Ncedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cn"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "ncedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vN"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Ncaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vn"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "ncaron"
comment|// Symbol #329 (ï¿½n) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"NG"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "ENG"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ng"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "eng"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=O"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Omacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=o"
argument_list|,
literal|"Å?"
argument_list|)
expr_stmt|;
comment|// "omacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uO"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Obreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uo"
argument_list|,
literal|"Å?"
argument_list|)
expr_stmt|;
comment|// "obreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"HO"
argument_list|,
literal|"Å?"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|CHARS
operator|.
name|put
argument_list|(
literal|"Ho"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|CHARS
operator|.
name|put
argument_list|(
literal|"OE"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "OElig"
name|CHARS
operator|.
name|put
argument_list|(
literal|"oe"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "oelig"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'R"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Racute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'r"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "racute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cR"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Rcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cr"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "rcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vR"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Rcaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vr"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "rcaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'S"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Sacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'s"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "sacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^S"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Scirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^s"
argument_list|,
literal|"Å?"
argument_list|)
expr_stmt|;
comment|// "scirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cS"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "Scedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cs"
argument_list|,
literal|"Å"
argument_list|)
expr_stmt|;
comment|// "scedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vS"
argument_list|,
literal|"Å "
argument_list|)
expr_stmt|;
comment|// "Scaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vs"
argument_list|,
literal|"Å¡"
argument_list|)
expr_stmt|;
comment|// "scaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"cT"
argument_list|,
literal|"Å¢"
argument_list|)
expr_stmt|;
comment|// "Tcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ct"
argument_list|,
literal|"Å£"
argument_list|)
expr_stmt|;
comment|// "tcedil"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vT"
argument_list|,
literal|"Å¤"
argument_list|)
expr_stmt|;
comment|// "Tcaron"
comment|// Symbol #357 (tï¿½) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"Tstrok"
argument_list|,
literal|"Å¦"
argument_list|)
expr_stmt|;
comment|// "Tstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"tstrok"
argument_list|,
literal|"Å§"
argument_list|)
expr_stmt|;
comment|// "tstrok"
name|CHARS
operator|.
name|put
argument_list|(
literal|"~U"
argument_list|,
literal|"Å¨"
argument_list|)
expr_stmt|;
comment|// "Utilde"
name|CHARS
operator|.
name|put
argument_list|(
literal|"~u"
argument_list|,
literal|"Å©"
argument_list|)
expr_stmt|;
comment|// "utilde"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=U"
argument_list|,
literal|"Åª"
argument_list|)
expr_stmt|;
comment|// "Umacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"=u"
argument_list|,
literal|"Å«"
argument_list|)
expr_stmt|;
comment|// "umacr"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uU"
argument_list|,
literal|"Å¬"
argument_list|)
expr_stmt|;
comment|// "Ubreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"uu"
argument_list|,
literal|"Å­"
argument_list|)
expr_stmt|;
comment|// "ubreve"
name|CHARS
operator|.
name|put
argument_list|(
literal|"rU"
argument_list|,
literal|"Å®"
argument_list|)
expr_stmt|;
comment|// "Uring"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ru"
argument_list|,
literal|"Å¯"
argument_list|)
expr_stmt|;
comment|// "uring"
name|CHARS
operator|.
name|put
argument_list|(
literal|"HU"
argument_list|,
literal|"Å¯"
argument_list|)
expr_stmt|;
comment|// "Odblac"
name|CHARS
operator|.
name|put
argument_list|(
literal|"Hu"
argument_list|,
literal|"Å±"
argument_list|)
expr_stmt|;
comment|// "odblac"
name|CHARS
operator|.
name|put
argument_list|(
literal|"kU"
argument_list|,
literal|"Å²"
argument_list|)
expr_stmt|;
comment|// "Uogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"ku"
argument_list|,
literal|"Å³"
argument_list|)
expr_stmt|;
comment|// "uogon"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^W"
argument_list|,
literal|"Å´"
argument_list|)
expr_stmt|;
comment|// "Wcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^w"
argument_list|,
literal|"Åµ"
argument_list|)
expr_stmt|;
comment|// "wcirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^Y"
argument_list|,
literal|"Å¶"
argument_list|)
expr_stmt|;
comment|// "Ycirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"^y"
argument_list|,
literal|"Å·"
argument_list|)
expr_stmt|;
comment|// "ycirc"
name|CHARS
operator|.
name|put
argument_list|(
literal|"\"Y"
argument_list|,
literal|"Å¸"
argument_list|)
expr_stmt|;
comment|// "Yuml"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'Z"
argument_list|,
literal|"Å¹"
argument_list|)
expr_stmt|;
comment|// "Zacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|"'z"
argument_list|,
literal|"Åº"
argument_list|)
expr_stmt|;
comment|// "zacute"
name|CHARS
operator|.
name|put
argument_list|(
literal|".Z"
argument_list|,
literal|"Å»"
argument_list|)
expr_stmt|;
comment|// "Zdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|".z"
argument_list|,
literal|"Å¼"
argument_list|)
expr_stmt|;
comment|// "zdot"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vZ"
argument_list|,
literal|"Å½"
argument_list|)
expr_stmt|;
comment|// "Zcaron"
name|CHARS
operator|.
name|put
argument_list|(
literal|"vz"
argument_list|,
literal|"Å¾"
argument_list|)
expr_stmt|;
comment|// "zcaron"
comment|// Symbol #383 (f) has no special Latex command
name|CHARS
operator|.
name|put
argument_list|(
literal|"%"
argument_list|,
literal|"%"
argument_list|)
expr_stmt|;
comment|// percent sign
block|}
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|int
name|i
decl_stmt|;
name|field
operator|=
name|field
operator|.
name|replaceAll
argument_list|(
literal|"&|\\\\&"
argument_list|,
literal|"&amp;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"[\\n]{1,}"
argument_list|,
literal|"<p>"
argument_list|)
expr_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|currentCommand
init|=
literal|null
decl_stmt|;
name|char
name|c
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|,
name|incommand
init|=
literal|false
decl_stmt|;
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|field
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|field
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
if|if
condition|(
name|incommand
condition|)
block|{
comment|/* Close Command */
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
block|}
name|escaped
operator|=
literal|true
expr_stmt|;
name|incommand
operator|=
literal|true
expr_stmt|;
name|currentCommand
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|incommand
operator|&&
operator|(
name|c
operator|==
literal|'{'
operator|||
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
comment|// Swallow the brace.
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isLetter
argument_list|(
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|'%'
operator|)
operator|||
operator|(
name|Globals
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|contains
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|c
argument_list|)
argument_list|)
operator|)
condition|)
block|{
name|escaped
operator|=
literal|false
expr_stmt|;
if|if
condition|(
operator|!
name|incommand
condition|)
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|// Else we are in a command, and should not keep the letter.
else|else
block|{
name|currentCommand
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|testCharCom
label|:
if|if
condition|(
operator|(
name|currentCommand
operator|.
name|length
argument_list|()
operator|==
literal|1
operator|)
operator|&&
operator|(
name|Globals
operator|.
name|SPECIAL_COMMAND_CHARS
operator|.
name|contains
argument_list|(
name|currentCommand
operator|.
name|toString
argument_list|()
argument_list|)
operator|)
condition|)
block|{
comment|// This indicates that we are in a command of the type
comment|// \^o or \~{n}
if|if
condition|(
name|i
operator|>=
name|field
operator|.
name|length
argument_list|()
operator|-
literal|1
condition|)
break|break
name|testCharCom
break|;
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
name|i
operator|++
expr_stmt|;
name|c
operator|=
name|field
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// System.out.println("next: "+(char)c);
name|String
name|combody
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|String
name|part
init|=
name|Util
operator|.
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|length
argument_list|()
expr_stmt|;
name|combody
operator|=
name|part
expr_stmt|;
block|}
else|else
block|{
name|combody
operator|=
name|field
operator|.
name|substring
argument_list|(
name|i
argument_list|,
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// System.out.println("... "+combody);
block|}
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|combody
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
name|incommand
operator|=
literal|false
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
comment|//	Are we already at the end of the string?
if|if
condition|(
name|i
operator|+
literal|1
operator|==
name|field
operator|.
name|length
argument_list|()
condition|)
block|{
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
comment|/* If found, then use translated version. If not,                              * then keep                              * the text of the parameter intact.                              */
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
name|String
name|argument
decl_stmt|;
if|if
condition|(
operator|!
name|incommand
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
argument_list|)
operator|||
operator|(
name|c
operator|==
literal|'{'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'}'
operator|)
condition|)
block|{
comment|// First test if we are already at the end of the string.
comment|// if (i>= field.length()-1)
comment|// break testContent;
name|String
name|command
init|=
name|currentCommand
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'{'
condition|)
block|{
name|String
name|part
init|=
name|Util
operator|.
name|getPart
argument_list|(
name|field
argument_list|,
name|i
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|i
operator|+=
name|part
operator|.
name|length
argument_list|()
expr_stmt|;
name|argument
operator|=
name|part
expr_stmt|;
if|if
condition|(
name|argument
operator|!=
literal|null
condition|)
block|{
comment|// handle common case of general latex command
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
operator|+
name|argument
argument_list|)
decl_stmt|;
comment|// System.out.print("command: "+command+", arg: "+argument);
comment|// System.out.print(", result: ");
comment|// If found, then use translated version. If not, then keep
comment|// the
comment|// text of the parameter intact.
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|argument
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
comment|// This end brace terminates a command. This can be the case in
comment|// constructs like {\aa}. The correct behaviour should be to
comment|// substitute the evaluated command and swallow the brace:
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// If the command is unknown, just print it:
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Object
name|result
init|=
name|CHARS
operator|.
name|get
argument_list|(
name|command
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|String
operator|)
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* else if (c == '}') {                     System.out.printf("com term by }: '%s'\n", currentCommand.toString());                      argument = "";                  }*/
else|else
block|{
comment|/*                      * TODO: this point is reached, apparently, if a command is                      * terminated in a strange way, such as with "$\omega$".                      * Also, the command "\&" causes us to get here. The former                      * issue is maybe a little difficult to address, since it                      * involves the LaTeX math mode. We don't have a complete                      * LaTeX parser, so maybe it's better to ignore these                      * commands?                      */
block|}
name|incommand
operator|=
literal|false
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

