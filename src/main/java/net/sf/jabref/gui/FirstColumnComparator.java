begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_class
DECL|class|FirstColumnComparator
class|class
name|FirstColumnComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|database
specifier|private
specifier|final
name|BibtexDatabase
name|database
decl_stmt|;
DECL|method|FirstColumnComparator (BibtexDatabase database)
specifier|public
name|FirstColumnComparator
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibtexEntry e1, BibtexEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|e1
parameter_list|,
name|BibtexEntry
name|e2
parameter_list|)
block|{
name|int
name|score1
init|=
literal|0
decl_stmt|;
name|int
name|score2
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|e1
operator|.
name|hasAllRequiredFields
argument_list|(
name|database
argument_list|)
condition|)
block|{
name|score1
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|e2
operator|.
name|hasAllRequiredFields
argument_list|(
name|database
argument_list|)
condition|)
block|{
name|score2
operator|++
expr_stmt|;
block|}
return|return
name|score1
operator|-
name|score2
return|;
block|}
block|}
end_class

end_unit

