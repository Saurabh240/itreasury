# Storage Module

This module provides a unified interface for file storage operations with support for multiple storage providers including local filesystem, AWS S3, and Azure Blob Storage.

## Configuration

### Storage Type Selection
```properties
app.storage.type=local  # Options: local, aws, azure
```

### Local Storage Configuration
```properties
# Base directory for local storage (default: ${user.home}/app-storage)
app.storage.local.base-dir=/path/to/storage/directory
```

### AWS Configuration
```properties
# Required AWS credentials
aws.access.key.id=${AWS_ACCESS_KEY_ID}
aws.access.key.secret=${AWS_SECRET_ACCESS_KEY}
aws.region=${AWS_REGION}
aws.s3.bucket=${AWS_S3_BUCKET}
```

### Azure Configuration
```properties
# Required Azure credentials
azure.storage.connection-string=${AZURE_STORAGE_CONNECTION_STRING}
azure.storage.container-name=${AZURE_STORAGE_CONTAINER_NAME}
```

### File Upload Configuration
```properties
# Maximum file size (default: 10MB)
app.storage.max-file-size=10MB

# Allowed file types (comma-separated)
app.storage.allowed-file-types=pdf,doc,docx,xls,xlsx,jpg,jpeg,png

# Base path for uploads
app.storage.upload-path-prefix=uploads
```

## Environment Variables

Create a `.env` file with the following variables:

```bash
# Local Storage Configuration (optional)
APP_STORAGE_LOCAL_BASE_DIR=/path/to/storage/directory

# AWS Configuration
AWS_ACCESS_KEY_ID=your_aws_access_key_here
AWS_SECRET_ACCESS_KEY=your_aws_secret_key_here
AWS_REGION=us-east-1
AWS_S3_BUCKET=your-bucket-name

# Azure Configuration
AZURE_STORAGE_CONNECTION_STRING=your_connection_string_here
AZURE_STORAGE_CONTAINER_NAME=your-container-name
```

## Usage

1. Inject the `StorageService` in your service:
```java
@RequiredArgsConstructor
public class YourService {
    private final StorageService storageService;
}
```

2. Use the service methods:
```java
// Upload a file
String filePath = storageService.uploadFile(file, "your/path");

// Download a file
InputStream fileStream = storageService.downloadFile(filePath);

// Get file URL
String fileUrl = storageService.getFileUrl(filePath);

// Delete a file
storageService.deleteFile(filePath);
```

## Storage Types

### Local Filesystem Storage
- Stores files on the local filesystem
- Good for development and testing
- Default storage type
- Files are stored under the configured base directory

### AWS S3 Storage
- Stores files in AWS S3
- Good for production environments
- Requires AWS credentials
- Files are stored in the configured S3 bucket

### Azure Blob Storage
- Stores files in Azure Blob Storage
- Good for production environments
- Requires Azure credentials
- Files are stored in the configured container

## File Validation

The storage service automatically validates:
- File size (must be less than configured max size)
- File type (must be in the allowed types list)

## Error Handling

The service throws `StorageException` for:
- File size exceeds limit
- Unsupported file type
- Upload/download failures
- Storage provider errors
- File system errors (for local storage) 