begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
comment|/**  *   * The |built_in| function {\.{purify\$}} pops the top (string) literal, removes  * nonalphanumeric characters except for |white_space| and |sep_char| characters  * (these get converted to a |space|) and removes certain alphabetic characters  * contained in the control sequences associated with a special character, and  * pushes the resulting string. If the literal isn't a string, it complains and  * pushes the null string.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|BibtexWidth
specifier|public
class|class
name|BibtexWidth
block|{
comment|/* 	 * Quoted from Bibtex: 	 *  	 * Now we initialize the system-dependent |char_width| array, for which 	 * |space| is the only |white_space| character given a nonzero printing 	 * width. The widths here are taken from Stanford's June~'87 $cmr10$~font 	 * and represent hundredths of a point (rounded), but since they're used 	 * only for relative comparisons, the units have no meaning. 	 */
DECL|field|widths
specifier|static
name|int
index|[]
name|widths
decl_stmt|;
DECL|method|getSpecialCharWidth (char[] c, int pos)
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
name|pos
operator|+
literal|1
operator|<
name|c
operator|.
name|length
condition|)
block|{
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'o'
operator|&&
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
condition|)
return|return
literal|778
return|;
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'O'
operator|&&
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
condition|)
return|return
literal|1014
return|;
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'a'
operator|&&
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'e'
condition|)
return|return
literal|722
return|;
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'A'
operator|&&
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'E'
condition|)
return|return
literal|903
return|;
if|if
condition|(
name|c
index|[
name|pos
index|]
operator|==
literal|'s'
operator|&&
name|c
index|[
name|pos
operator|+
literal|1
index|]
operator|==
literal|'s'
condition|)
return|return
literal|500
return|;
block|}
return|return
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
name|widths
operator|==
literal|null
condition|)
block|{
comment|// Watch out octals!!
name|widths
operator|=
operator|new
name|int
index|[
literal|0200
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
literal|0200
condition|;
name|i
operator|++
control|)
block|{
name|widths
index|[
name|i
index|]
operator|=
literal|0
expr_stmt|;
block|}
comment|// Watch out octals!!
name|widths
index|[
literal|040
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|041
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|042
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|043
index|]
operator|=
literal|833
expr_stmt|;
name|widths
index|[
literal|044
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|045
index|]
operator|=
literal|833
expr_stmt|;
name|widths
index|[
literal|046
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|047
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|050
index|]
operator|=
literal|389
expr_stmt|;
name|widths
index|[
literal|051
index|]
operator|=
literal|389
expr_stmt|;
name|widths
index|[
literal|052
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|053
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|054
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|055
index|]
operator|=
literal|333
expr_stmt|;
name|widths
index|[
literal|056
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|057
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|060
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|061
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|062
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|063
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|064
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|065
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|066
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|067
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|070
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|071
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|072
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|073
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|074
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|075
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|076
index|]
operator|=
literal|472
expr_stmt|;
name|widths
index|[
literal|077
index|]
operator|=
literal|472
expr_stmt|;
name|widths
index|[
literal|0100
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|0101
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0102
index|]
operator|=
literal|708
expr_stmt|;
name|widths
index|[
literal|0103
index|]
operator|=
literal|722
expr_stmt|;
name|widths
index|[
literal|0104
index|]
operator|=
literal|764
expr_stmt|;
name|widths
index|[
literal|0105
index|]
operator|=
literal|681
expr_stmt|;
name|widths
index|[
literal|0106
index|]
operator|=
literal|653
expr_stmt|;
name|widths
index|[
literal|0107
index|]
operator|=
literal|785
expr_stmt|;
name|widths
index|[
literal|0110
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0111
index|]
operator|=
literal|361
expr_stmt|;
name|widths
index|[
literal|0112
index|]
operator|=
literal|514
expr_stmt|;
name|widths
index|[
literal|0113
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|0114
index|]
operator|=
literal|625
expr_stmt|;
name|widths
index|[
literal|0115
index|]
operator|=
literal|917
expr_stmt|;
name|widths
index|[
literal|0116
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0117
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|0120
index|]
operator|=
literal|681
expr_stmt|;
name|widths
index|[
literal|0121
index|]
operator|=
literal|778
expr_stmt|;
name|widths
index|[
literal|0122
index|]
operator|=
literal|736
expr_stmt|;
name|widths
index|[
literal|0123
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0124
index|]
operator|=
literal|722
expr_stmt|;
name|widths
index|[
literal|0125
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0126
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0127
index|]
operator|=
literal|1028
expr_stmt|;
name|widths
index|[
literal|0130
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0131
index|]
operator|=
literal|750
expr_stmt|;
name|widths
index|[
literal|0132
index|]
operator|=
literal|611
expr_stmt|;
name|widths
index|[
literal|0133
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0134
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0135
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0136
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0137
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0140
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0141
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0142
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0143
index|]
operator|=
literal|444
expr_stmt|;
name|widths
index|[
literal|0144
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0145
index|]
operator|=
literal|444
expr_stmt|;
name|widths
index|[
literal|0146
index|]
operator|=
literal|306
expr_stmt|;
name|widths
index|[
literal|0147
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0150
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0151
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0152
index|]
operator|=
literal|306
expr_stmt|;
name|widths
index|[
literal|0153
index|]
operator|=
literal|528
expr_stmt|;
name|widths
index|[
literal|0154
index|]
operator|=
literal|278
expr_stmt|;
name|widths
index|[
literal|0155
index|]
operator|=
literal|833
expr_stmt|;
name|widths
index|[
literal|0156
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0157
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0160
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0161
index|]
operator|=
literal|528
expr_stmt|;
name|widths
index|[
literal|0162
index|]
operator|=
literal|392
expr_stmt|;
name|widths
index|[
literal|0163
index|]
operator|=
literal|394
expr_stmt|;
name|widths
index|[
literal|0164
index|]
operator|=
literal|389
expr_stmt|;
name|widths
index|[
literal|0165
index|]
operator|=
literal|556
expr_stmt|;
name|widths
index|[
literal|0166
index|]
operator|=
literal|528
expr_stmt|;
name|widths
index|[
literal|0167
index|]
operator|=
literal|722
expr_stmt|;
name|widths
index|[
literal|0170
index|]
operator|=
literal|528
expr_stmt|;
name|widths
index|[
literal|0171
index|]
operator|=
literal|528
expr_stmt|;
name|widths
index|[
literal|0172
index|]
operator|=
literal|444
expr_stmt|;
name|widths
index|[
literal|0173
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0174
index|]
operator|=
literal|1000
expr_stmt|;
name|widths
index|[
literal|0175
index|]
operator|=
literal|500
expr_stmt|;
name|widths
index|[
literal|0176
index|]
operator|=
literal|500
expr_stmt|;
block|}
if|if
condition|(
literal|0
operator|<=
name|c
operator|&&
name|c
operator|<
literal|0200
condition|)
block|{
return|return
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
comment|/** 	 *  	 * @param toMeasure 	 * @param warn 	 *            may-be-null 	 * @return 	 */
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
comment|/* 		 * From Bibtex: We use the natural width for all but special characters, 		 * and we complain if the string isn't brace-balanced. 		 */
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
comment|/* 		 * From Bibtex: 		 *  		 * We use the natural widths of all characters except that some 		 * characters have no width: braces, control sequences (except for the 		 * usual 13 accented and foreign characters, whose widths are given in 		 * the next module), and |white_space| following control sequences (even 		 * a null control sequence). 		 *  		 */
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
name|braceLevel
operator|==
literal|1
operator|&&
name|i
operator|+
literal|1
operator|<
name|n
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
name|i
argument_list|<
name|n
operator|&&
name|braceLevel
argument_list|>
literal|0
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
name|i
operator|<
name|n
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
name|i
operator|<
name|n
operator|&&
name|i
operator|==
name|afterBackslash
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
name|i
operator|<
name|n
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
name|i
argument_list|<
name|n
operator|&&
name|braceLevel
argument_list|>
literal|0
operator|&&
name|c
index|[
name|i
index|]
operator|!=
literal|'\\'
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
name|result
operator|+=
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

