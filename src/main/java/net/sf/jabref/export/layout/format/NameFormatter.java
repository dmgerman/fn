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
name|logic
operator|.
name|AuthorList
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|bst
operator|.
name|BibtexNameFormatter
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

begin_comment
comment|/**  * This layout formatter uses the Bibtex name.format$ method and provides ultimate flexibility:  *   * The formatter needs a parameter to be passed in that follows the following format:  *   *<case1>@<range11>@"<format>"@<range12>@"<format>"@<range13>...@@  *   *<case2>@<range21>@... and so on.  *  * Individual cases are separated by @@ and items in a case by @.  *   * Cases are just integers or the character * and will tell the formatter to apply the following formats if there are   * less or equal authors given to it. The cases must be in strict increasing order with the * in the last position.   *   * For instance:  *   * case1 = 2  * case2 = 3  * case3 = *  *   * Ranges are either<integer>..<integer>,<integer> or the character * using a 1 based index for indexing   * authors from the given authorlist. Integer indexes can be negative to denote them to start from   * the end of the list where -1 is the last author.  *   * For instance with an authorlist of "Joe Doe and Mary Jane and Bruce Bar and Arthur Kay":  *   * 1..3 will affect Joe, Mary and Bruce  *   * 4..4 will affect Arthur  *   * * will affect all of them  *   * 2..-1 will affect Mary, Bruce and Arthur  *   * The<format> uses the Bibtex formatter format:  *   * The four letter v, f, l, j indicate the name parts von, first, last, jr which   * are used within curly braces. A single letter v, f, l, j indicates that the name should be abbreviated.  * To put a quote in the format string quote it using \" (mh. this doesn't work yet)  *   * I give some examples but would rather point you to the bibtex documentation.  *   * "{ll}, {f}." Will turn "Joe Doe" into "Doe, J."  *   * Complete example:  *   * To turn:   *   * "Joe Doe and Mary Jane and Bruce Bar and Arthur Kay"  *   * into   *   * "Doe, J., Jane, M., Bar, B. and Kay, A."  *   * you would use  *   * 1@*@{ll}, {f}.@@2@1@{ll}, {f}.@2@ and {ll}, {f}.@@*@1..-3@{ll}, {f}., @-2@{ll}, {f}.@-1@ and {ll}, {f}.  *   * Yeah this is trouble-some to write, but should work.  *   * For more examples see the test-cases.  *  */
end_comment

begin_class
DECL|class|NameFormatter
specifier|public
class|class
name|NameFormatter
implements|implements
name|LayoutFormatter
block|{
DECL|field|DEFAULT_FORMAT
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_FORMAT
init|=
literal|"1@*@{ff }{vv }{ll}{, jj}@@*@1@{ff }{vv }{ll}{, jj}@*@, {ff }{vv }{ll}{, jj}"
decl_stmt|;
DECL|method|format (String toFormat, AuthorList al, String[] formats)
specifier|private
name|String
name|format
parameter_list|(
name|String
name|toFormat
parameter_list|,
name|AuthorList
name|al
parameter_list|,
name|String
index|[]
name|formats
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|n
init|=
name|al
operator|.
name|size
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
name|al
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|1
init|;
name|j
operator|<
name|formats
operator|.
name|length
condition|;
name|j
operator|+=
literal|2
control|)
block|{
if|if
condition|(
name|formats
index|[
name|j
index|]
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|toFormat
argument_list|,
name|i
argument_list|,
name|formats
index|[
name|j
operator|+
literal|1
index|]
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
block|{
name|String
index|[]
name|range
init|=
name|formats
index|[
name|j
index|]
operator|.
name|split
argument_list|(
literal|"\\.\\."
argument_list|)
decl_stmt|;
name|int
name|s
decl_stmt|;
name|int
name|e
decl_stmt|;
if|if
condition|(
name|range
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|s
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|range
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|e
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|range
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|s
operator|=
name|e
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|range
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|<
literal|0
condition|)
block|{
name|s
operator|+=
name|n
operator|+
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|<
literal|0
condition|)
block|{
name|e
operator|+=
name|n
operator|+
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|<
name|s
condition|)
block|{
name|int
name|temp
init|=
name|e
decl_stmt|;
name|e
operator|=
name|s
expr_stmt|;
name|s
operator|=
name|temp
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|<=
name|i
operator|&&
name|i
operator|<=
name|e
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|toFormat
argument_list|,
name|i
argument_list|,
name|formats
index|[
name|j
operator|+
literal|1
index|]
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|format (String toFormat, String parameters, BibtexEntry currentEntry)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|toFormat
parameter_list|,
name|String
name|parameters
parameter_list|,
name|BibtexEntry
name|currentEntry
parameter_list|)
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|toFormat
argument_list|)
decl_stmt|;
if|if
condition|(
name|parameters
operator|==
literal|null
operator|||
name|parameters
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|parameters
operator|=
literal|"*:*:\"{ff}{vv}{ll}{,jj} \""
expr_stmt|;
block|}
name|String
index|[]
name|cases
init|=
name|parameters
operator|.
name|split
argument_list|(
literal|"@@"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|aCase
range|:
name|cases
control|)
block|{
name|String
index|[]
name|formatString
init|=
name|aCase
operator|.
name|split
argument_list|(
literal|"@"
argument_list|)
decl_stmt|;
if|if
condition|(
name|formatString
operator|.
name|length
operator|<
literal|3
condition|)
block|{
comment|// Error
return|return
name|toFormat
return|;
block|}
if|if
condition|(
name|formatString
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
return|return
name|format
argument_list|(
name|toFormat
argument_list|,
name|al
argument_list|,
name|formatString
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|al
operator|.
name|size
argument_list|()
operator|<=
name|Integer
operator|.
name|parseInt
argument_list|(
name|formatString
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
return|return
name|format
argument_list|(
name|toFormat
argument_list|,
name|al
argument_list|,
name|formatString
argument_list|)
return|;
block|}
block|}
block|}
return|return
name|toFormat
return|;
block|}
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
name|format
argument_list|(
name|fieldText
argument_list|,
name|parameter
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|field|parameter
specifier|private
name|String
name|parameter
init|=
name|NameFormatter
operator|.
name|DEFAULT_FORMAT
decl_stmt|;
DECL|method|setParameter (String parameter)
specifier|public
name|void
name|setParameter
parameter_list|(
name|String
name|parameter
parameter_list|)
block|{
name|this
operator|.
name|parameter
operator|=
name|parameter
expr_stmt|;
block|}
block|}
end_class

end_unit

