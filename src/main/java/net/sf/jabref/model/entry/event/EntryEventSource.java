begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry.event
package|package
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
name|event
package|;
end_package

begin_comment
comment|/**  * This enum represents the context EntryEvents were sent from.  */
end_comment

begin_enum
DECL|enum|EntryEventSource
specifier|public
enum|enum
name|EntryEventSource
block|{
DECL|enumConstant|LOCAL
name|LOCAL
block|,
DECL|enumConstant|SHARED
name|SHARED
block|,
DECL|enumConstant|UNDO
name|UNDO
block|,
DECL|enumConstant|SAVE_ACTION
name|SAVE_ACTION
block|}
end_enum

end_unit

