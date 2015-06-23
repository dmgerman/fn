begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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

begin_class
DECL|class|CaseKeeper
specifier|public
class|class
name|CaseKeeper
implements|implements
name|LayoutFormatter
block|{
DECL|method|CaseKeeper ()
specifier|public
name|CaseKeeper
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|format (String text, String[] listOfWords)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|,
name|String
index|[]
name|listOfWords
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Arrays
operator|.
name|sort
argument_list|(
name|listOfWords
argument_list|,
operator|new
name|LengthComparator
argument_list|()
argument_list|)
expr_stmt|;
comment|// For each word in the list
for|for
control|(
name|String
name|listOfWord
range|:
name|listOfWords
control|)
block|{
comment|// Add {} if the character before is a space, -, /, (, [, ", or } or if it is at the start of the string but not if it is followed by a }
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"(^|[- /\\[(}\"])"
operator|+
name|listOfWord
operator|+
literal|"($|[^}])"
argument_list|,
literal|"$1\\{"
operator|+
name|listOfWord
operator|+
literal|"\\}$2"
argument_list|)
expr_stmt|;
block|}
return|return
name|text
return|;
block|}
annotation|@
name|Override
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
specifier|final
name|CaseKeeperList
name|list
init|=
operator|new
name|CaseKeeperList
argument_list|()
decl_stmt|;
return|return
name|this
operator|.
name|format
argument_list|(
name|text
argument_list|,
name|list
operator|.
name|getAll
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

begin_class
DECL|class|LengthComparator
class|class
name|LengthComparator
implements|implements
name|Comparator
argument_list|<
name|String
argument_list|>
block|{
annotation|@
name|Override
DECL|method|compare (String o1, String o2)
specifier|public
name|int
name|compare
parameter_list|(
name|String
name|o1
parameter_list|,
name|String
name|o2
parameter_list|)
block|{
if|if
condition|(
name|o1
operator|.
name|length
argument_list|()
operator|>
name|o2
operator|.
name|length
argument_list|()
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
elseif|else
if|if
condition|(
name|o1
operator|.
name|length
argument_list|()
operator|==
name|o2
operator|.
name|length
argument_list|()
condition|)
block|{
return|return
literal|0
return|;
block|}
return|return
literal|1
return|;
block|}
block|}
end_class

end_unit

