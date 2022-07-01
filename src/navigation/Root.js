import React from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {NavigationContainer} from '@react-navigation/native';

const Stack = createNativeStackNavigator();

//pages
import Home from '../pages/Home';
import TaskTest from '../pages/TaskTest';

export default function Root() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="TaskTest">
        <Stack.Screen name="Home" component={Home} options={{title: 'Home'}} />
        <Stack.Screen
          name="TaskTest"
          component={TaskTest}
          options={{title: 'TaskTest'}}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
