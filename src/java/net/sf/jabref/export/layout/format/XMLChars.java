begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  Filename: $RCSfile$
end_comment

begin_comment
comment|//  Purpose:  Atom representation.
end_comment

begin_comment
comment|//  Language: Java
end_comment

begin_comment
comment|//  Compiler: JDK 1.4
end_comment

begin_comment
comment|//  Authors:  Joerg K. Wegner, Morten O. Alver
end_comment

begin_comment
comment|//  Version:  $Revision$
end_comment

begin_comment
comment|//            $Date$
end_comment

begin_comment
comment|//            $Author$
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is free software; you can redistribute it and/or modify
end_comment

begin_comment
comment|//  it under the terms of the GNU General Public License as published by
end_comment

begin_comment
comment|//  the Free Software Foundation version 2 of the License.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is distributed in the hope that it will be useful,
end_comment

begin_comment
comment|//  but WITHOUT ANY WARRANTY; without even the implied warranty of
end_comment

begin_comment
comment|//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
end_comment

begin_comment
comment|//  GNU General Public License for more details.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
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
name|regex
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * Changes {\^o} or {\^{o}} to ?  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|XMLChars
specifier|public
class|class
name|XMLChars
implements|implements
name|LayoutFormatter
block|{
comment|//~ Methods ////////////////////////////////////////////////////////////////
comment|//Pattern pattern = Pattern.compile(".*\\{..[a-zA-Z].\\}.*");
DECL|field|pattern
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*\\{\\\\.*[a-zA-Z]\\}.*"
argument_list|)
decl_stmt|;
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|fieldText
operator|=
name|firstFormat
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
comment|//if (!pattern.matcher(fieldText).matches())
comment|//    return restFormat(fieldText);
for|for
control|(
name|Iterator
name|i
init|=
name|Globals
operator|.
name|HTML_CHARS
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|s
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|repl
init|=
operator|(
name|String
operator|)
name|Globals
operator|.
name|XML_CHARS
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|repl
operator|!=
literal|null
condition|)
name|fieldText
operator|=
name|fieldText
operator|.
name|replaceAll
argument_list|(
name|s
argument_list|,
name|repl
argument_list|)
expr_stmt|;
block|}
comment|//RemoveBrackets rb = new RemoveBrackets();
return|return
name|restFormat
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
DECL|method|firstFormat (String s)
specifier|private
name|String
name|firstFormat
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|replaceAll
argument_list|(
literal|"&|\\\\&"
argument_list|,
literal|"&#x0026;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"--"
argument_list|,
literal|"&#x2013;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|,
literal|"-"
argument_list|)
return|;
block|}
DECL|method|restFormat (String s)
specifier|private
name|String
name|restFormat
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|String
name|fieldText
init|=
name|s
operator|.
name|replaceAll
argument_list|(
literal|"\\}"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\{"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|// now some copy-paste problems most often occuring in abstracts when copied from PDF
comment|// AND: this is accepted in the abstract of bibtex files, so are forced to catch those cases
name|int
name|code
decl_stmt|;
name|char
name|character
decl_stmt|;
name|StringBuffer
name|buffer
init|=
operator|new
name|StringBuffer
argument_list|(
name|fieldText
operator|.
name|length
argument_list|()
operator|<<
literal|1
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fieldText
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|character
operator|=
name|fieldText
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|code
operator|=
operator|(
operator|(
name|int
operator|)
name|character
operator|)
operator|&
literal|255
expr_stmt|;
comment|//System.out.println(""+character+" "+code);
if|if
condition|(
operator|(
name|code
operator|<
literal|40
operator|&&
name|code
operator|!=
literal|32
operator|)
operator|||
name|code
operator|>
literal|125
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
literal|"&#"
operator|+
name|code
operator|+
literal|";"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// TODO: highly inefficient, create look-up array with all 255 codes only once and use code as key!!!
name|int
index|[]
name|forceReplace
init|=
operator|new
name|int
index|[]
block|{
literal|44
block|,
literal|45
block|,
literal|63
block|,
literal|64
block|,
literal|94
block|,
literal|95
block|,
literal|96
block|,
literal|124
block|}
decl_stmt|;
name|boolean
name|alphabet
init|=
literal|true
decl_stmt|;
for|for
control|(
name|int
name|ii
init|=
literal|0
init|;
name|ii
operator|<
name|forceReplace
operator|.
name|length
condition|;
name|ii
operator|++
control|)
block|{
if|if
condition|(
name|code
operator|==
name|forceReplace
index|[
name|ii
index|]
condition|)
block|{
name|buffer
operator|.
name|append
argument_list|(
literal|"&#"
operator|+
name|code
operator|+
literal|";"
argument_list|)
expr_stmt|;
name|alphabet
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
comment|// force roundtripping
if|if
condition|(
name|alphabet
condition|)
name|buffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|code
argument_list|)
expr_stmt|;
block|}
block|}
name|fieldText
operator|=
name|buffer
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// use common abbreviations for<,> instead of code
for|for
control|(
name|Iterator
name|i
init|=
name|Globals
operator|.
name|ASCII2XML_CHARS
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|ss
init|=
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|repl
init|=
operator|(
name|String
operator|)
name|Globals
operator|.
name|ASCII2XML_CHARS
operator|.
name|get
argument_list|(
name|ss
argument_list|)
decl_stmt|;
if|if
condition|(
name|repl
operator|!=
literal|null
condition|)
name|fieldText
operator|=
name|fieldText
operator|.
name|replaceAll
argument_list|(
name|ss
argument_list|,
name|repl
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldText
return|;
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

