begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
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
name|io
operator|.
name|Reader
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
name|Files
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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|TestIconsProperties
specifier|public
class|class
name|TestIconsProperties
block|{
annotation|@
name|Test
DECL|method|testExistenceOfIconImagesReferencedFromIconsProperties ()
specifier|public
name|void
name|testExistenceOfIconImagesReferencedFromIconsProperties
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|folder
init|=
literal|"src/main/resources/images/external"
decl_stmt|;
name|String
name|iconsProperties
init|=
literal|"Icons.properties"
decl_stmt|;
name|String
name|iconsPropertiesPath
init|=
literal|"src/main/resources/images/"
operator|+
name|iconsProperties
decl_stmt|;
comment|// load properties
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
try|try
init|(
name|Reader
name|reader
init|=
name|Files
operator|.
name|newBufferedReader
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|iconsPropertiesPath
argument_list|)
argument_list|)
init|)
block|{
name|properties
operator|.
name|load
argument_list|(
name|reader
argument_list|)
expr_stmt|;
block|}
name|assertFalse
argument_list|(
name|properties
operator|.
name|entrySet
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|,
literal|"There must be loaded properties after loading "
operator|+
name|iconsPropertiesPath
argument_list|)
expr_stmt|;
comment|// check that each key references an existing file
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|entry
range|:
name|properties
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|name
init|=
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|folder
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|,
literal|"Referenced image ("
operator|+
name|name
operator|+
literal|" --> "
operator|+
name|value
operator|+
literal|" does not exist in folder "
operator|+
name|folder
argument_list|)
expr_stmt|;
block|}
comment|// check that each image in the folder is referenced by a key
name|List
argument_list|<
name|String
argument_list|>
name|imagesReferencedFromProperties
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Object
argument_list|,
name|Object
argument_list|>
name|entry
range|:
name|properties
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|imagesReferencedFromProperties
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|pathStream
init|=
name|Files
operator|.
name|list
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|folder
argument_list|)
argument_list|)
init|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fileNamesInFolder
init|=
name|pathStream
operator|.
name|map
argument_list|(
name|p
lambda|->
name|p
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|fileNamesInFolder
operator|.
name|removeAll
argument_list|(
name|imagesReferencedFromProperties
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"[red.png]"
argument_list|,
name|fileNamesInFolder
operator|.
name|toString
argument_list|()
argument_list|,
literal|"Images are in the folder that are unused"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

