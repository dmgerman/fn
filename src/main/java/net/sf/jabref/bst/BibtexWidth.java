begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// $Id$
end_comment

begin_package
DECL|package|net.sf.jabref.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
package|;
end_package

begin_comment
comment|/**  *   * The |built_in| function {\.{purify\$}} pops the top (string) literal, removes  * nonalphanumeric characters except for |white_space| and |sep_char| characters  * (these get converted to a |space|) and removes certain alphabetic characters  * contained in the control sequences associated with a special character, and  * pushes the resulting string. If the literal isn't a string, it complains and  * pushes the null string.  *   */
end_comment

begin_class
DECL|class|BibtexWidth
specifier|public
class|class
name|BibtexWidth
block|{
comment|/*      * Quoted from Bibtex:      *       * Now we initialize the system-dependent |char_width| array, for which      * |space| is the only |white_space| character given a nonzero printing      * width. The widths here are taken from Stanford's June~'87 $cmr10$~font      * and represent hundredths of a point (rounded), but since they're used      * only for relative comparisons, the units have no meaning.      */
DECL|field|widths
specifier|private
specifier|static
name|int
index|[]
name|widths
decl_stmt|;
DECL|method|getSpecialCharWidth (char[] c, int pos)
specifier|private
specifier|static
name|int
name|getSpecialCharWidth
parameter_list|(
name|char
index|[]
name|c
parameter_list|,
name|int
name|pos
parameter_list|)
block|{
if|if
condition|(
operator|(
name|pos
operator|+
literal|1
operator|)
operator|<
name|c
operator|.
name|length
condition|)
block|{
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'o'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
operator|)
condition|)
block|{
return|return
literal|778
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'O'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
operator|)
condition|)
block|{
return|return
literal|1014
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'a'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
operator|)
condition|)
block|{
return|return
literal|722
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'A'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
operator|)
condition|)
block|{
return|return
literal|903
return|;
block|}
if|if
condition|(
operator|(
name|c
index|[
name|pos
index|]
operator|==
literal|'s'
operator|)
operator|&&
operator|(
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'s'
operator|)
condition|)
block|{
return|return
literal|500
return|;
block|}
block|}
return|return
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
name|c
index|[
name|pos
index|]
argument_list|)
return|;
block|}
DECL|method|getCharWidth (char c)
specifier|public
specifier|static
name|int
name|getCharWidth
parameter_list|(
name|char
name|c
parameter_list|)
block|{
if|if
condition|(
name|BibtexWidth
operator|.
name|widths
operator|==
literal|null
condition|)
block|{
name|BibtexWidth
operator|.
name|widths
operator|=
operator|new
name|int
index|[
literal|128
index|]
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
literal|128
condition|;
name|i
operator|++
control|)
block|{
name|BibtexWidth
operator|.
name|widths
index|[
name|i
index|]
operator|=
literal|0
expr_stmt|;
block|}
name|BibtexWidth
operator|.
name|widths
index|[
literal|32
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|33
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|34
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|35
index|]
operator|=
literal|833
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|36
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|37
index|]
operator|=
literal|833
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|38
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|39
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|40
index|]
operator|=
literal|389
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|41
index|]
operator|=
literal|389
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|42
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|43
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|44
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|45
index|]
operator|=
literal|333
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|46
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|47
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|48
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|49
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|50
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|51
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|52
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|53
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|54
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|55
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|56
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|57
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|58
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|59
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|60
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|61
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|62
index|]
operator|=
literal|472
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|63
index|]
operator|=
literal|472
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|64
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|65
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|66
index|]
operator|=
literal|708
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|67
index|]
operator|=
literal|722
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|68
index|]
operator|=
literal|764
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|69
index|]
operator|=
literal|681
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|70
index|]
operator|=
literal|653
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|71
index|]
operator|=
literal|785
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|72
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|73
index|]
operator|=
literal|361
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|74
index|]
operator|=
literal|514
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|75
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|76
index|]
operator|=
literal|625
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|77
index|]
operator|=
literal|917
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|78
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|79
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|80
index|]
operator|=
literal|681
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|81
index|]
operator|=
literal|778
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|82
index|]
operator|=
literal|736
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|83
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|84
index|]
operator|=
literal|722
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|85
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|86
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|87
index|]
operator|=
literal|1028
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|88
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|89
index|]
operator|=
literal|750
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|90
index|]
operator|=
literal|611
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|91
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|92
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|93
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|94
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|95
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|96
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|97
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|98
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|99
index|]
operator|=
literal|444
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|100
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|101
index|]
operator|=
literal|444
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|102
index|]
operator|=
literal|306
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|103
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|104
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|105
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|106
index|]
operator|=
literal|306
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|107
index|]
operator|=
literal|528
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|108
index|]
operator|=
literal|278
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|109
index|]
operator|=
literal|833
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|110
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|111
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|112
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|113
index|]
operator|=
literal|528
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|114
index|]
operator|=
literal|392
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|115
index|]
operator|=
literal|394
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|116
index|]
operator|=
literal|389
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|117
index|]
operator|=
literal|556
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|118
index|]
operator|=
literal|528
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|119
index|]
operator|=
literal|722
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|120
index|]
operator|=
literal|528
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|121
index|]
operator|=
literal|528
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|122
index|]
operator|=
literal|444
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|123
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|124
index|]
operator|=
literal|1000
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|125
index|]
operator|=
literal|500
expr_stmt|;
name|BibtexWidth
operator|.
name|widths
index|[
literal|126
index|]
operator|=
literal|500
expr_stmt|;
block|}
if|if
condition|(
operator|(
literal|0
operator|<=
name|c
operator|)
operator|&&
operator|(
name|c
operator|<
literal|128
operator|)
condition|)
block|{
return|return
name|BibtexWidth
operator|.
name|widths
index|[
name|c
index|]
return|;
block|}
else|else
block|{
return|return
literal|0
return|;
block|}
block|}
comment|/**      *       * @param toMeasure      * @param warn      *            may-be-null      * @return      */
DECL|method|width (String toMeasure, Warn warn)
specifier|public
specifier|static
name|int
name|width
parameter_list|(
name|String
name|toMeasure
parameter_list|,
name|Warn
name|warn
parameter_list|)
block|{
comment|/*          * From Bibtex: We use the natural width for all but special characters,          * and we complain if the string isn't brace-balanced.          */
name|int
name|i
init|=
literal|0
decl_stmt|;
name|int
name|n
init|=
name|toMeasure
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|braceLevel
init|=
literal|0
decl_stmt|;
name|char
index|[]
name|c
init|=
name|toMeasure
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|int
name|result
init|=
literal|0
decl_stmt|;
comment|/*          * From Bibtex:          *           * We use the natural widths of all characters except that some          * characters have no width: braces, control sequences (except for the          * usual 13 accented and foreign characters, whose widths are given in          * the next module), and |white_space| following control sequences (even          * a null control sequence).          *           */
while|while
condition|(
name|i
operator|<
name|n
condition|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
condition|)
block|{
name|braceLevel
operator|++
expr_stmt|;
if|if
condition|(
operator|(
name|braceLevel
operator|==
literal|1
operator|)
operator|&&
operator|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|n
operator|)
operator|&&
operator|(
name|c
index|[
name|i
operator|+
literal|1
index|]
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
comment|// skip brace
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
comment|// skip backslash
name|int
name|afterBackslash
init|=
name|i
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|i
operator|==
name|afterBackslash
operator|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
comment|// Skip non-alpha control seq
block|}
else|else
block|{
if|if
condition|(
name|BibtexCaseChanger
operator|.
name|findSpecialChar
argument_list|(
name|c
argument_list|,
name|afterBackslash
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|result
operator|+=
name|BibtexWidth
operator|.
name|getSpecialCharWidth
argument_list|(
name|c
argument_list|,
name|afterBackslash
argument_list|)
expr_stmt|;
block|}
block|}
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
name|Character
operator|.
name|isWhitespace
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|i
operator|++
expr_stmt|;
block|}
while|while
condition|(
operator|(
name|i
operator|<
name|n
operator|)
operator|&&
operator|(
name|braceLevel
operator|>
literal|0
operator|)
operator|&&
operator|(
name|c
index|[
name|i
index|]
operator|!=
literal|'\\'
operator|)
condition|)
block|{
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'}'
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'{'
condition|)
block|{
name|braceLevel
operator|++
expr_stmt|;
block|}
else|else
block|{
name|result
operator|+=
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
block|}
continue|continue;
block|}
block|}
elseif|else
if|if
condition|(
name|c
index|[
name|i
index|]
operator|==
literal|'}'
condition|)
block|{
if|if
condition|(
name|braceLevel
operator|>
literal|0
condition|)
block|{
name|braceLevel
operator|--
expr_stmt|;
block|}
else|else
block|{
name|BibtexCaseChanger
operator|.
name|complain
argument_list|(
name|toMeasure
argument_list|)
expr_stmt|;
block|}
block|}
name|result
operator|+=
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
name|c
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|BibtexCaseChanger
operator|.
name|checkBrace
argument_list|(
name|toMeasure
argument_list|,
name|braceLevel
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

