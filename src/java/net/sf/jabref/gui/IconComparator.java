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
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * Comparator that handles icon columns.   */
end_comment

begin_class
DECL|class|IconComparator
specifier|public
class|class
name|IconComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
decl_stmt|;
DECL|method|IconComparator (String[] fields)
specifier|public
name|IconComparator
parameter_list|(
name|String
index|[]
name|fields
parameter_list|)
block|{
name|this
operator|.
name|fields
operator|=
name|fields
expr_stmt|;
block|}
comment|/**      * Replaced old Method (see below) to give this Comparator the      * capability to sort with more levels than "HasGotOrHasNot"      *     public int compare(BibtexEntry e1, BibtexEntry e2) {          for (int i=0; i<fields.length; i++) {             String val1 = e1.getField(fields[i]),                     val2 = e2.getField(fields[i]);             if (val1 == null) {                 if (val2 != null)                     return 1;             } else {                 if (val2 == null)                     return -1;             }         }         return 0;     }     */
comment|/**      * Replacement of old Method - Works slightly different      */
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
comment|// First get the Field Values
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|val1
init|=
name|e1
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|,
name|val2
init|=
name|e2
operator|.
name|getField
argument_list|(
name|fields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
comment|// Try to cast the Field Values to an Integer
comment|// Leave at "0" if casting failed
name|int
name|v1
init|=
literal|0
decl_stmt|;
try|try
block|{
name|v1
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|val1
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{             	             }
name|int
name|v2
init|=
literal|0
decl_stmt|;
try|try
block|{
name|v2
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|val2
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{             	             }
comment|// Compare Values like a usual Integer
if|if
condition|(
name|v1
operator|<
name|v2
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
name|v1
operator|>
name|v2
condition|)
block|{
return|return
literal|1
return|;
block|}
else|else
block|{
return|return
literal|0
return|;
block|}
block|}
return|return
literal|0
return|;
block|}
block|}
end_class

end_unit

