begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.label
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|label
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_class
DECL|class|KeyWord
specifier|public
class|class
name|KeyWord
extends|extends
name|HashSet
argument_list|<
name|String
argument_list|>
block|{
DECL|field|singleton
specifier|private
specifier|static
name|KeyWord
name|singleton
decl_stmt|;
DECL|method|KeyWord ()
specifier|private
name|KeyWord
parameter_list|()
block|{
comment|// puts all keywords in
name|add
argument_list|(
literal|"society"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"transaction"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"transactions"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"journal"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"review"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"revue"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"communication"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"communications"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"letters"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"advances"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"proceedings"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"proceeding"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"international"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"joint"
argument_list|)
expr_stmt|;
name|add
argument_list|(
literal|"conference"
argument_list|)
expr_stmt|;
block|}
DECL|method|getKeyWord ()
specifier|public
specifier|static
name|KeyWord
name|getKeyWord
parameter_list|()
block|{
if|if
condition|(
name|singleton
operator|==
literal|null
condition|)
name|singleton
operator|=
operator|new
name|KeyWord
argument_list|()
expr_stmt|;
return|return
name|singleton
return|;
block|}
DECL|method|isKeyWord (String matchWord)
specifier|public
name|boolean
name|isKeyWord
parameter_list|(
name|String
name|matchWord
parameter_list|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|matchWord
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|isKeyWordMatchCase (String matchWord)
specifier|public
name|boolean
name|isKeyWordMatchCase
parameter_list|(
name|String
name|matchWord
parameter_list|)
block|{
if|if
condition|(
name|contains
argument_list|(
name|matchWord
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

