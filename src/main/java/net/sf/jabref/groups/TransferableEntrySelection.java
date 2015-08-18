begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|TransferableEntrySelection
class|class
name|TransferableEntrySelection
implements|implements
name|Transferable
block|{
DECL|field|flavorInternal
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|flavorInternal
decl_stmt|;
DECL|field|flavorExternal
specifier|private
specifier|static
specifier|final
name|DataFlavor
name|flavorExternal
decl_stmt|;
DECL|field|flavors
specifier|private
specifier|static
specifier|final
name|DataFlavor
index|[]
name|flavors
decl_stmt|;
DECL|field|selectedEntries
specifier|private
specifier|final
name|BibtexEntry
index|[]
name|selectedEntries
decl_stmt|;
DECL|field|selectedEntriesCiteKeys
specifier|private
specifier|final
name|String
name|selectedEntriesCiteKeys
decl_stmt|;
DECL|field|includeCiteKeyword
specifier|private
name|boolean
name|includeCiteKeyword
decl_stmt|;
static|static
block|{
name|DataFlavor
name|df1
init|=
literal|null
decl_stmt|;
name|DataFlavor
name|df2
init|=
literal|null
decl_stmt|;
try|try
block|{
name|df1
operator|=
operator|new
name|DataFlavor
argument_list|(
name|DataFlavor
operator|.
name|javaJVMLocalObjectMimeType
operator|+
literal|";class=net.sf.jabref.groups.TransferableEntrySelection"
argument_list|)
expr_stmt|;
name|df2
operator|=
name|DataFlavor
operator|.
name|getTextPlainUnicodeFlavor
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// never happens
block|}
name|flavorInternal
operator|=
name|df1
expr_stmt|;
name|flavorExternal
operator|=
name|df2
expr_stmt|;
name|flavors
operator|=
operator|new
name|DataFlavor
index|[]
block|{
name|TransferableEntrySelection
operator|.
name|flavorInternal
block|,
name|TransferableEntrySelection
operator|.
name|flavorExternal
block|}
expr_stmt|;
block|}
DECL|method|TransferableEntrySelection (BibtexEntry[] selectedEntries)
specifier|public
name|TransferableEntrySelection
parameter_list|(
name|BibtexEntry
index|[]
name|selectedEntries
parameter_list|)
block|{
name|this
operator|.
name|selectedEntries
operator|=
name|selectedEntries
expr_stmt|;
name|StringBuilder
name|keys
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|selectedEntries
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
name|keys
operator|.
name|append
argument_list|(
name|selectedEntries
index|[
name|i
index|]
operator|.
name|getCiteKey
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|+
literal|1
operator|<
name|selectedEntries
operator|.
name|length
condition|)
block|{
name|keys
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
name|selectedEntriesCiteKeys
operator|=
name|keys
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
name|TransferableEntrySelection
operator|.
name|flavors
return|;
block|}
annotation|@
name|Override
DECL|method|isDataFlavorSupported (DataFlavor someFlavor)
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|someFlavor
parameter_list|)
block|{
return|return
name|someFlavor
operator|.
name|equals
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorInternal
argument_list|)
operator|||
name|someFlavor
operator|.
name|equals
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorExternal
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTransferData (DataFlavor someFlavor)
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|someFlavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
throws|,
name|IOException
block|{
if|if
condition|(
operator|!
name|isDataFlavorSupported
argument_list|(
name|someFlavor
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|someFlavor
argument_list|)
throw|;
block|}
if|if
condition|(
name|someFlavor
operator|.
name|equals
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorInternal
argument_list|)
condition|)
block|{
return|return
name|this
return|;
block|}
name|String
name|s
init|=
name|includeCiteKeyword
condition|?
literal|"\\cite{"
operator|+
name|selectedEntriesCiteKeys
operator|+
literal|"}"
else|:
name|selectedEntriesCiteKeys
decl_stmt|;
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|s
operator|.
name|getBytes
argument_list|(
name|TransferableEntrySelection
operator|.
name|flavorExternal
operator|.
name|getParameter
argument_list|(
literal|"charset"
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getSelection ()
specifier|public
name|BibtexEntry
index|[]
name|getSelection
parameter_list|()
block|{
return|return
name|selectedEntries
return|;
block|}
DECL|method|setIncludeCiteKeyword (boolean includeCiteKeyword)
specifier|public
name|void
name|setIncludeCiteKeyword
parameter_list|(
name|boolean
name|includeCiteKeyword
parameter_list|)
block|{
name|this
operator|.
name|includeCiteKeyword
operator|=
name|includeCiteKeyword
expr_stmt|;
block|}
block|}
end_class

end_unit

