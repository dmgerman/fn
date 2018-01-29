begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.event
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|event
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|event
operator|.
name|EntryEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|event
operator|.
name|GroupUpdatedEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|event
operator|.
name|MetaDataChangedEvent
import|;
end_import

begin_comment
comment|/**  * This Event is automatically fired at the same time as {@link EntryEvent}, {@link GroupUpdatedEvent} or {@link MetaDataChangedEvent}.  */
end_comment

begin_class
DECL|class|BibDatabaseContextChangedEvent
specifier|public
class|class
name|BibDatabaseContextChangedEvent
block|{
comment|// no data
block|}
end_class

end_unit

