# Generated by the protocol buffer compiler.  DO NOT EDIT!

from google.protobuf import descriptor
from google.protobuf import message
from google.protobuf import reflection
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)



DESCRIPTOR = descriptor.FileDescriptor(
  name='MmAnywhere.proto',
  package='org.ratatosk.mmrest.data',
  serialized_pb='\n\x10MmAnywhere.proto\x12\x18org.ratatosk.mmrest.data\"W\n\x10MmDevicesListing\x12\x43\n\x10mmDeviceListings\x18\x01 \x03(\x0b\x32).org.ratatosk.mmrest.data.MmDeviceListing\"\xa7\x01\n\x0fMmDeviceListing\x12\x10\n\x08\x64\x65viceId\x18\x01 \x02(\t\x12\x13\n\x0b\x64\x65viceLabel\x18\x02 \x02(\t\x12\x12\n\ndeviceType\x18\x03 \x02(\t\x12\x11\n\tdeviceUrl\x18\x04 \x02(\t\x12\x46\n\x12mmDeviceProperties\x18\x05 \x03(\x0b\x32*.org.ratatosk.mmrest.data.MmDeviceProperty\"\x7f\n\x10MmDeviceProperty\x12\x12\n\npropertyId\x18\x01 \x02(\t\x12\x15\n\rpropertyValue\x18\x02 \x02(\t\x12\x15\n\rpropertyLabel\x18\x03 \x02(\t\x12\x14\n\x0cpropertyType\x18\x04 \x02(\t\x12\x13\n\x0bpropertyUrl\x18\x05 \x02(\t*Q\n\x0fMmPropertyTypes\x12\r\n\tUNDEFINED\x10\x00\x12\x0b\n\x07\x42OOLEAN\x10\x01\x12\x0b\n\x07INTEGER\x10\x02\x12\t\n\x05\x46LOAT\x10\x03\x12\n\n\x06STRING\x10\x04')

_MMPROPERTYTYPES = descriptor.EnumDescriptor(
  name='MmPropertyTypes',
  full_name='org.ratatosk.mmrest.data.MmPropertyTypes',
  filename=None,
  file=DESCRIPTOR,
  values=[
    descriptor.EnumValueDescriptor(
      name='UNDEFINED', index=0, number=0,
      options=None,
      type=None),
    descriptor.EnumValueDescriptor(
      name='BOOLEAN', index=1, number=1,
      options=None,
      type=None),
    descriptor.EnumValueDescriptor(
      name='INTEGER', index=2, number=2,
      options=None,
      type=None),
    descriptor.EnumValueDescriptor(
      name='FLOAT', index=3, number=3,
      options=None,
      type=None),
    descriptor.EnumValueDescriptor(
      name='STRING', index=4, number=4,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=434,
  serialized_end=515,
)


UNDEFINED = 0
BOOLEAN = 1
INTEGER = 2
FLOAT = 3
STRING = 4



_MMDEVICESLISTING = descriptor.Descriptor(
  name='MmDevicesListing',
  full_name='org.ratatosk.mmrest.data.MmDevicesListing',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='mmDeviceListings', full_name='org.ratatosk.mmrest.data.MmDevicesListing.mmDeviceListings', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=46,
  serialized_end=133,
)


_MMDEVICELISTING = descriptor.Descriptor(
  name='MmDeviceListing',
  full_name='org.ratatosk.mmrest.data.MmDeviceListing',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='deviceId', full_name='org.ratatosk.mmrest.data.MmDeviceListing.deviceId', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='deviceLabel', full_name='org.ratatosk.mmrest.data.MmDeviceListing.deviceLabel', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='deviceType', full_name='org.ratatosk.mmrest.data.MmDeviceListing.deviceType', index=2,
      number=3, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='deviceUrl', full_name='org.ratatosk.mmrest.data.MmDeviceListing.deviceUrl', index=3,
      number=4, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='mmDeviceProperties', full_name='org.ratatosk.mmrest.data.MmDeviceListing.mmDeviceProperties', index=4,
      number=5, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=136,
  serialized_end=303,
)


_MMDEVICEPROPERTY = descriptor.Descriptor(
  name='MmDeviceProperty',
  full_name='org.ratatosk.mmrest.data.MmDeviceProperty',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='propertyId', full_name='org.ratatosk.mmrest.data.MmDeviceProperty.propertyId', index=0,
      number=1, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='propertyValue', full_name='org.ratatosk.mmrest.data.MmDeviceProperty.propertyValue', index=1,
      number=2, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='propertyLabel', full_name='org.ratatosk.mmrest.data.MmDeviceProperty.propertyLabel', index=2,
      number=3, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='propertyType', full_name='org.ratatosk.mmrest.data.MmDeviceProperty.propertyType', index=3,
      number=4, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='propertyUrl', full_name='org.ratatosk.mmrest.data.MmDeviceProperty.propertyUrl', index=4,
      number=5, type=9, cpp_type=9, label=2,
      has_default_value=False, default_value=unicode("", "utf-8"),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=305,
  serialized_end=432,
)

_MMDEVICESLISTING.fields_by_name['mmDeviceListings'].message_type = _MMDEVICELISTING
_MMDEVICELISTING.fields_by_name['mmDeviceProperties'].message_type = _MMDEVICEPROPERTY
DESCRIPTOR.message_types_by_name['MmDevicesListing'] = _MMDEVICESLISTING
DESCRIPTOR.message_types_by_name['MmDeviceListing'] = _MMDEVICELISTING
DESCRIPTOR.message_types_by_name['MmDeviceProperty'] = _MMDEVICEPROPERTY

class MmDevicesListing(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _MMDEVICESLISTING
  
  # @@protoc_insertion_point(class_scope:org.ratatosk.mmrest.data.MmDevicesListing)

class MmDeviceListing(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _MMDEVICELISTING
  
  # @@protoc_insertion_point(class_scope:org.ratatosk.mmrest.data.MmDeviceListing)

class MmDeviceProperty(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _MMDEVICEPROPERTY
  
  # @@protoc_insertion_point(class_scope:org.ratatosk.mmrest.data.MmDeviceProperty)

# @@protoc_insertion_point(module_scope)
