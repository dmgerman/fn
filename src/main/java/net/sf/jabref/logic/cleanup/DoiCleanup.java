begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|logic
operator|.
name|FieldChange
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|logic
operator|.
name|util
operator|.
name|DOI
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
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Formats the DOI (e.g. removes http part) and also moves DOIs from note, url or ee field to the doi field.  */
end_comment

begin_class
DECL|class|DoiCleanup
specifier|public
class|class
name|DoiCleanup
implements|implements
name|CleanupJob
block|{
comment|/**      * Fields to check for DOIs.      */
DECL|field|FIELDS
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|FIELDS
init|=
block|{
literal|"note"
block|,
literal|"url"
block|,
literal|"ee"
block|}
decl_stmt|;
annotation|@
name|Override
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First check if the Doi Field is empty
if|if
condition|(
name|entry
operator|.
name|hasField
argument_list|(
literal|"doi"
argument_list|)
condition|)
block|{
name|String
name|doiFieldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|build
argument_list|(
name|doiFieldValue
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|newValue
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|doiFieldValue
operator|.
name|equals
argument_list|(
name|newValue
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"doi"
argument_list|,
name|doiFieldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|change
argument_list|)
expr_stmt|;
block|}
comment|// Doi field seems to contain Doi -> cleanup note, url, ee field
for|for
control|(
name|String
name|field
range|:
name|FIELDS
control|)
block|{
name|DOI
operator|.
name|build
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
operator|(
name|field
operator|)
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|unused
lambda|->
name|removeFieldValue
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|changes
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// As the Doi field is empty we now check if note, url, or ee field contains a Doi
for|for
control|(
name|String
name|field
range|:
name|FIELDS
control|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doi
init|=
name|DOI
operator|.
name|build
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|doi
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// update Doi
name|String
name|oldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
decl_stmt|;
name|String
name|newValue
init|=
name|doi
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|FieldChange
name|change
init|=
operator|new
name|FieldChange
argument_list|(
name|entry
argument_list|,
literal|"doi"
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
name|changes
operator|.
name|add
argument_list|(
name|change
argument_list|)
expr_stmt|;
name|removeFieldValue
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|changes
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|changes
return|;
block|}
DECL|method|removeFieldValue (BibEntry entry, String field, List<FieldChange> changes)
specifier|private
name|void
name|removeFieldValue
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|field
parameter_list|,
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
parameter_list|)
block|{
name|CleanupJob
name|eraser
init|=
operator|new
name|FieldFormatterCleanup
argument_list|(
name|field
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
decl_stmt|;
name|changes
operator|.
name|addAll
argument_list|(
name|eraser
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

