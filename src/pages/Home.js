import {View, Text, Button, TouchableHighlight} from 'react-native';
import React from 'react';

export default function Home({navigation: {navigate}}) {
  return (
    <View>
      <Text>Home</Text>
      <TouchableHighlight onPress={() => navigate('TaskTest')}>
        <Text style={{fontSize: 68}}>페이지 이동</Text>
      </TouchableHighlight>
    </View>
  );
}
