begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
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
package|;
end_package

begin_comment
comment|/**  *  Stores all informations needed to manage entries on a shared (SQL) database.  */
end_comment

begin_class
DECL|class|SharedBibEntryData
specifier|public
class|class
name|SharedBibEntryData
block|{
comment|// This id is set by the remote database system (DBS).
comment|// It has to be unique on remote DBS for all connected JabRef instances.
comment|// The old id above does not satisfy this requirement.
DECL|field|sharedID
specifier|private
name|int
name|sharedID
decl_stmt|;
comment|// Needed for version controlling if used on shared database
DECL|field|version
specifier|private
name|int
name|version
decl_stmt|;
DECL|method|SharedBibEntryData ()
specifier|public
name|SharedBibEntryData
parameter_list|()
block|{
name|this
operator|.
name|sharedID
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|version
operator|=
literal|1
expr_stmt|;
block|}
DECL|method|getSharedID ()
specifier|public
name|int
name|getSharedID
parameter_list|()
block|{
return|return
name|sharedID
return|;
block|}
DECL|method|setSharedID (int sharedID)
specifier|public
name|void
name|setSharedID
parameter_list|(
name|int
name|sharedID
parameter_list|)
block|{
name|this
operator|.
name|sharedID
operator|=
name|sharedID
expr_stmt|;
block|}
DECL|method|getVersion ()
specifier|public
name|int
name|getVersion
parameter_list|()
block|{
return|return
name|version
return|;
block|}
DECL|method|setVersion (int version)
specifier|public
name|void
name|setVersion
parameter_list|(
name|int
name|version
parameter_list|)
block|{
name|this
operator|.
name|version
operator|=
name|version
expr_stmt|;
block|}
block|}
end_class

end_unit

