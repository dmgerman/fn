begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout
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
package|;
end_package

begin_import
import|import
name|wsi
operator|.
name|ra
operator|.
name|types
operator|.
name|StringInt
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * DOCUMENT ME!  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|Layout
specifier|public
class|class
name|Layout
block|{
comment|//~ Instance fields ////////////////////////////////////////////////////////
DECL|field|layoutEntries
specifier|private
name|LayoutEntry
index|[]
name|layoutEntries
decl_stmt|;
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|Layout (Vector parsedEntries, String classPrefix)
specifier|public
name|Layout
parameter_list|(
name|Vector
name|parsedEntries
parameter_list|,
name|String
name|classPrefix
parameter_list|)
throws|throws
name|Exception
block|{
name|StringInt
name|si
decl_stmt|;
name|Vector
name|tmpEntries
init|=
operator|new
name|Vector
argument_list|(
name|parsedEntries
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
comment|//layoutEntries=new LayoutEntry[parsedEntries.size()];
name|Vector
name|blockEntries
init|=
literal|null
decl_stmt|;
name|LayoutEntry
name|le
decl_stmt|;
name|String
name|blockStart
init|=
literal|null
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
name|parsedEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|si
operator|=
operator|(
name|StringInt
operator|)
name|parsedEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|//System.out.println("PARSED: "+si.s+"="+si.i);
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_LAYOUT_TEXT
condition|)
block|{             }
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_SIMPLE_FIELD
condition|)
block|{             }
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_START
condition|)
block|{
name|blockEntries
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|blockStart
operator|=
name|si
operator|.
name|s
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_FIELD_END
condition|)
block|{
if|if
condition|(
name|blockStart
operator|.
name|equals
argument_list|(
name|si
operator|.
name|s
argument_list|)
condition|)
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|si
argument_list|)
expr_stmt|;
name|le
operator|=
operator|new
name|LayoutEntry
argument_list|(
name|blockEntries
argument_list|,
name|classPrefix
argument_list|)
expr_stmt|;
name|tmpEntries
operator|.
name|add
argument_list|(
name|le
argument_list|)
expr_stmt|;
name|blockEntries
operator|=
literal|null
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
literal|"Nested field entries are not implemented !!!"
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|si
operator|.
name|i
operator|==
name|LayoutHelper
operator|.
name|IS_OPTION_FIELD
condition|)
block|{             }
comment|//			else if (si.i == LayoutHelper.IS_OPTION_FIELD_PARAM)
comment|//			{
comment|//			}
if|if
condition|(
name|blockEntries
operator|==
literal|null
condition|)
block|{
name|tmpEntries
operator|.
name|add
argument_list|(
operator|new
name|LayoutEntry
argument_list|(
name|si
argument_list|,
name|classPrefix
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|blockEntries
operator|.
name|add
argument_list|(
name|si
argument_list|)
expr_stmt|;
block|}
block|}
name|layoutEntries
operator|=
operator|new
name|LayoutEntry
index|[
name|tmpEntries
operator|.
name|size
argument_list|()
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
name|tmpEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|layoutEntries
index|[
name|i
index|]
operator|=
operator|(
name|LayoutEntry
operator|)
name|tmpEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|//System.out.println(layoutEntries[i].text);
block|}
block|}
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|method|doLayout (BibtexEntry bibtex)
specifier|public
name|String
name|doLayout
parameter_list|(
name|BibtexEntry
name|bibtex
parameter_list|)
block|{
comment|//System.out.println("LAYOUT: " + bibtex.getId());
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|String
name|fieldText
decl_stmt|;
name|boolean
name|previousSkipped
init|=
literal|false
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
name|layoutEntries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|fieldText
operator|=
name|layoutEntries
index|[
name|i
index|]
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|)
expr_stmt|;
comment|//System.out.println("'" + fieldText + "'");
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|(
name|i
operator|+
literal|1
operator|)
operator|<
name|layoutEntries
operator|.
name|length
condition|)
block|{
if|if
condition|(
name|layoutEntries
index|[
name|i
operator|+
literal|1
index|]
operator|.
name|doLayout
argument_list|(
name|bibtex
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|//sb.append("MISSING");
name|i
operator|++
expr_stmt|;
name|previousSkipped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
block|}
block|}
else|else
block|{
comment|// if previous was skipped --> remove leading line breaks
if|if
condition|(
name|previousSkipped
condition|)
block|{
name|int
name|eol
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\n'
operator|)
operator|||
operator|(
name|fieldText
operator|.
name|charAt
argument_list|(
name|eol
argument_list|)
operator|==
literal|'\r'
operator|)
operator|)
condition|)
block|{
name|eol
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|eol
operator|<
name|fieldText
operator|.
name|length
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|fieldText
operator|.
name|substring
argument_list|(
name|eol
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//System.out.println("ENTRY-BLOCK: " + layoutEntries[i].doLayout(bibtex));
name|sb
operator|.
name|append
argument_list|(
name|fieldText
argument_list|)
expr_stmt|;
block|}
block|}
name|previousSkipped
operator|=
literal|false
expr_stmt|;
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

