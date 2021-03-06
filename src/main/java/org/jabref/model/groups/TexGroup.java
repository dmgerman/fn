begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
package|;
end_package

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
name|java
operator|.
name|net
operator|.
name|InetAddress
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|auxparser
operator|.
name|AuxParser
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
name|auxparser
operator|.
name|AuxParserResult
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
name|entry
operator|.
name|BibEntry
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
name|MetaData
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
name|util
operator|.
name|FileHelper
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
name|util
operator|.
name|FileUpdateListener
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
name|util
operator|.
name|FileUpdateMonitor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|TexGroup
specifier|public
class|class
name|TexGroup
extends|extends
name|AbstractGroup
implements|implements
name|FileUpdateListener
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|TexGroup
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|filePath
specifier|private
name|Path
name|filePath
decl_stmt|;
DECL|field|keysUsedInAux
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|keysUsedInAux
init|=
literal|null
decl_stmt|;
DECL|field|fileMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileMonitor
decl_stmt|;
DECL|field|auxParser
specifier|private
name|AuxParser
name|auxParser
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|user
specifier|private
name|String
name|user
decl_stmt|;
DECL|method|TexGroup (String name, GroupHierarchyType context, Path filePath, AuxParser auxParser, FileUpdateMonitor fileMonitor, MetaData metaData, String user)
name|TexGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Path
name|filePath
parameter_list|,
name|AuxParser
name|auxParser
parameter_list|,
name|FileUpdateMonitor
name|fileMonitor
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|user
operator|=
name|user
expr_stmt|;
name|this
operator|.
name|filePath
operator|=
name|expandPath
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|filePath
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|auxParser
operator|=
name|auxParser
expr_stmt|;
name|this
operator|.
name|fileMonitor
operator|=
name|fileMonitor
expr_stmt|;
block|}
DECL|method|TexGroup (String name, GroupHierarchyType context, Path filePath, AuxParser auxParser, FileUpdateMonitor fileMonitor, MetaData metaData)
name|TexGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Path
name|filePath
parameter_list|,
name|AuxParser
name|auxParser
parameter_list|,
name|FileUpdateMonitor
name|fileMonitor
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|filePath
argument_list|,
name|auxParser
argument_list|,
name|fileMonitor
argument_list|,
name|metaData
argument_list|,
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.name"
argument_list|)
operator|+
literal|'-'
operator|+
name|InetAddress
operator|.
name|getLocalHost
argument_list|()
operator|.
name|getHostName
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|create (String name, GroupHierarchyType context, Path filePath, AuxParser auxParser, FileUpdateMonitor fileMonitor, MetaData metaData)
specifier|public
specifier|static
name|TexGroup
name|create
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Path
name|filePath
parameter_list|,
name|AuxParser
name|auxParser
parameter_list|,
name|FileUpdateMonitor
name|fileMonitor
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
throws|throws
name|IOException
block|{
name|TexGroup
name|group
init|=
operator|new
name|TexGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|filePath
argument_list|,
name|auxParser
argument_list|,
name|fileMonitor
argument_list|,
name|metaData
argument_list|)
decl_stmt|;
name|fileMonitor
operator|.
name|addListenerForFile
argument_list|(
name|filePath
argument_list|,
name|group
argument_list|)
expr_stmt|;
return|return
name|group
return|;
block|}
DECL|method|createWithoutFileMonitoring (String name, GroupHierarchyType context, Path filePath, AuxParser auxParser, FileUpdateMonitor fileMonitor, MetaData metaData)
specifier|public
specifier|static
name|TexGroup
name|createWithoutFileMonitoring
parameter_list|(
name|String
name|name
parameter_list|,
name|GroupHierarchyType
name|context
parameter_list|,
name|Path
name|filePath
parameter_list|,
name|AuxParser
name|auxParser
parameter_list|,
name|FileUpdateMonitor
name|fileMonitor
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
throws|throws
name|IOException
block|{
return|return
operator|new
name|TexGroup
argument_list|(
name|name
argument_list|,
name|context
argument_list|,
name|filePath
argument_list|,
name|auxParser
argument_list|,
name|fileMonitor
argument_list|,
name|metaData
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|keysUsedInAux
operator|==
literal|null
condition|)
block|{
name|AuxParserResult
name|auxResult
init|=
name|auxParser
operator|.
name|parse
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
name|keysUsedInAux
operator|=
name|auxResult
operator|.
name|getUniqueKeys
argument_list|()
expr_stmt|;
block|}
return|return
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|map
argument_list|(
name|keysUsedInAux
operator|::
name|contains
argument_list|)
operator|.
name|orElse
argument_list|(
literal|false
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|TexGroup
argument_list|(
name|name
operator|.
name|getValue
argument_list|()
argument_list|,
name|context
argument_list|,
name|filePath
argument_list|,
name|auxParser
argument_list|,
name|fileMonitor
argument_list|,
name|metaData
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|// This should never happen because we were able to monitor the file just fine until now
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem creating copy of group"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|super
operator|.
name|equals
argument_list|(
name|o
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|TexGroup
name|group
init|=
operator|(
name|TexGroup
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|filePath
argument_list|,
name|group
operator|.
name|filePath
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"TexGroup{"
operator|+
literal|"filePath="
operator|+
name|filePath
operator|+
literal|", keysUsedInAux="
operator|+
name|keysUsedInAux
operator|+
literal|", auxParser="
operator|+
name|auxParser
operator|+
literal|", fileMonitor="
operator|+
name|fileMonitor
operator|+
literal|"} "
operator|+
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|super
operator|.
name|hashCode
argument_list|()
argument_list|,
name|filePath
argument_list|)
return|;
block|}
DECL|method|getFilePath ()
specifier|public
name|Path
name|getFilePath
parameter_list|()
block|{
return|return
name|relativize
argument_list|(
name|filePath
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|fileUpdated ()
specifier|public
name|void
name|fileUpdated
parameter_list|()
block|{
comment|// Reset previous parse result
name|keysUsedInAux
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|relativize (Path path)
specifier|private
name|Path
name|relativize
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
init|=
name|getFileDirectoriesAsPaths
argument_list|()
decl_stmt|;
return|return
name|FileHelper
operator|.
name|relativize
argument_list|(
name|path
argument_list|,
name|fileDirectories
argument_list|)
return|;
block|}
DECL|method|expandPath (Path path)
specifier|private
name|Path
name|expandPath
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirectories
init|=
name|getFileDirectoriesAsPaths
argument_list|()
decl_stmt|;
return|return
name|FileHelper
operator|.
name|expandFilenameAsPath
argument_list|(
name|path
operator|.
name|toString
argument_list|()
argument_list|,
name|fileDirectories
argument_list|)
operator|.
name|orElse
argument_list|(
name|path
argument_list|)
return|;
block|}
DECL|method|getFileDirectoriesAsPaths ()
specifier|private
name|List
argument_list|<
name|Path
argument_list|>
name|getFileDirectoriesAsPaths
parameter_list|()
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|metaData
operator|.
name|getLaTexFileDirectory
argument_list|(
name|user
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fileDirs
operator|::
name|add
argument_list|)
expr_stmt|;
return|return
name|fileDirs
return|;
block|}
block|}
end_class

end_unit

