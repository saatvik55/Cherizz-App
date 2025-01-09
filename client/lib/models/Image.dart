class ImageModel {
  final String imageUrl;
  final DateTime uploadedAt;

  ImageModel({required this.imageUrl, required this.uploadedAt});

  factory ImageModel.fromJson(Map<String, dynamic> json) {
    return ImageModel(
      imageUrl: json['imageUrl'],
      uploadedAt: DateTime.parse(json['uploadedAt']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'imageUrl': imageUrl,
      'uploadedAt': uploadedAt.toIso8601String(),
    };
  }
}
