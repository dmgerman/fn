begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
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
name|FieldComparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * Comparator for sorting bibliography entries according to publication year. This is used to  * sort entries in multiple citations where the oldest publication should appear first.  */
end_comment

begin_class
DECL|class|YearComparator
class|class
name|YearComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|authComp
specifier|private
specifier|final
name|FieldComparator
name|authComp
init|=
operator|new
name|FieldComparator
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
DECL|field|editorComp
specifier|private
specifier|final
name|FieldComparator
name|editorComp
init|=
operator|new
name|FieldComparator
argument_list|(
literal|"editor"
argument_list|)
decl_stmt|;
DECL|field|yearComp
specifier|private
specifier|final
name|FieldComparator
name|yearComp
init|=
operator|new
name|FieldComparator
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
DECL|method|YearComparator ()
specifier|public
name|YearComparator
parameter_list|()
block|{      }
annotation|@
name|Override
DECL|method|compare (BibtexEntry o1, BibtexEntry o2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|o1
parameter_list|,
name|BibtexEntry
name|o2
parameter_list|)
block|{
comment|// Year as first criterion:
name|int
name|comp
init|=
name|yearComp
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
decl_stmt|;
if|if
condition|(
name|comp
operator|!=
literal|0
condition|)
block|{
return|return
name|comp
return|;
block|}
comment|// TODO: Is it a good idea to try editor if author fields are equal?
comment|// Author as next criterion:
name|comp
operator|=
name|authComp
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
expr_stmt|;
if|if
condition|(
name|comp
operator|!=
literal|0
condition|)
block|{
return|return
name|comp
return|;
block|}
comment|// Editor as next criterion:
return|return
name|editorComp
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
block|}
block|}
end_class

end_unit

